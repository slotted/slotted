/*
 * Copyright 2012 Jeffrey Kleiss
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.googlecode.slotted.client;

import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controls display of the RootSlot and all nested slots.
 */
public class SlottedController {
    /**
     * RootSlot instance that should be used to define {@link SlottedPlace}s that should be
     * displayed in the root slot.
     */
    public static final RootSlotImpl RootSlot = new RootSlotImpl();

    public static class RootSlotImpl extends Slot {
        public RootSlotImpl() {
            super(null, null);
        }

        public RootSlotImpl(SlottedPlace defaultPlace) {
            super(null, defaultPlace);
        }
    }

    /**
     * Default implementation of {@link Delegate}, based on {@link Window}.
     */
    public static class DefaultDelegate implements Delegate {
        public HandlerRegistration addWindowClosingHandler(ClosingHandler handler) {
            return Window.addWindowClosingHandler(handler);
        }

        public boolean confirm(String[] messages) {
            return Window.confirm(messages[0]);
        }
    }

    /**
     * Optional delegate in charge of Window-related events. Provides nice isolation for unit
     * testing, and allows customization of confirmation handling.
     */
    public interface Delegate {
        /**
         * Adds a {@link ClosingHandler} to the Delegate.
         *
         * @param handler a {@link ClosingHandler} instance
         * @return a {@link HandlerRegistration} instance
         */
        HandlerRegistration addWindowClosingHandler(ClosingHandler handler);

        /**
         * Called to confirm a window closing event.
         *
         * @param messages an array of warning messages
         * @return true to allow the window closing
         */
        boolean confirm(String[] messages);
    }

    private static final Logger log = Logger.getLogger(SlottedController.class.getName());

    private final EventBus eventBus;
    private final HistoryMapper historyMapper;
    private ActivityMapper legacyActivityMapper;

    private int goToCount = 0;
    private final Delegate delegate;
    private boolean reloadAll = false;
    private ActiveSlot root;
    private PlaceParameters currentParameters;
    private NavigationOverride navigationOverride;

    /**
     * Create a new SlottedController with a {@link DefaultDelegate}. The DefaultDelegate is created
     * via a call to GWT.create(), so an alternative default implementation can be provided through
     * &lt;replace-with&gt; rules in a {@code .gwt.xml} file.
     *
     * @param historyMapper the {@link HistoryMapper}
     * @param eventBus the {@link EventBus}
     */
    public SlottedController(HistoryMapper historyMapper, EventBus eventBus) {
        this(historyMapper, eventBus, (Delegate) GWT.create(DefaultDelegate.class));
    }

    /**
     * Create a new SlottedController.
     *
     * @param historyMapper the {@link HistoryMapper}
     * @param eventBus the {@link EventBus}
     * @param delegate the {@link Delegate} in charge of Window-related events
     */
    public SlottedController(HistoryMapper historyMapper, EventBus eventBus, Delegate delegate) {
        this.eventBus = eventBus;
        this.historyMapper = historyMapper;
        historyMapper.setController(this);

        this.delegate = delegate;
        delegate.addWindowClosingHandler(new ClosingHandler() {
            public void onWindowClosing(ClosingEvent event) {
                ArrayList<String> warnings = new ArrayList<String>();
                root.maybeGoTo(warnings);
                if (!warnings.isEmpty()) {
                    event.setMessage(warnings.get(0));
                }
            }
        });
    }

    //todo javadoc
    public void setLegacyMappers(ActivityMapper legacyActivityMapper,
            PlaceHistoryMapper legacyHistoryMapper, Place defaultPlace)
    {
        this.legacyActivityMapper = legacyActivityMapper;
        historyMapper.setLegacyHistoryMapper(legacyHistoryMapper);
        if (historyMapper.getDefaultPlace() == null) {
            historyMapper.registerDefaultPlace(
                    new WrappedPlace(defaultPlace, legacyActivityMapper));
        }
    }

    //todo javadoc
    public void setActivityMapper(ActivityMapper legacyActivityMapper) {
        this.legacyActivityMapper = legacyActivityMapper;
    }

    /**
     * Sets the widget that displays the root slot content.  This must be set before any content can
     * be displayed.
     *
     * @param display an instance of AcceptsOneWidget
     */
    public void setDisplay(AcceptsOneWidget display) {
        Slot rootSlot = new RootSlotImpl(null);
        rootSlot.setDisplay(display);
        root = new ActiveSlot(null, rootSlot, eventBus, this);

        History.fireCurrentHistoryState();
    }

    /**
     * Sets reloadAll (defaults false).  If the reloadAll is true, then every Activity in the
     * hierarchy will be stopped and started again on every navigation.  If false, Places/Activities
     * that are already in the hierarchy will not be stopped, but Activities not in navigation
     * will be stopped and new Places will have Activities started.
     *
     * It is possible to override the RefreshAll default value by calling {@link #goTo(SlottedPlace, SlottedPlace[], boolean)}.
     *
     * @param reloadAll The new default value to use on goTo() calls and URL changes.
     */
    public void setReloadAll(boolean reloadAll) {
        this.reloadAll = reloadAll;
    }

    //todo javadoc
    public void setNavigationOverride(NavigationOverride navigationOverride) {
        this.navigationOverride = navigationOverride;
    }

    /**
     * Used by the HistoryMapper to display the default place.  This method works as if you called
     * {@link #goTo(Place)} with the default place.
     */
    protected void goToDefaultPlace() {
        historyMapper.goToDefaultPlace();
    }

    /**
     * Request a change to a new place. It is not a given that we'll actually get there. First all
     * active {@link SlottedActivity#mayStop()} will called. If any activities return a warning
     * message, it will be presented to the user via {@link Delegate#confirm(String[])} (which is
     * typically a call to {@link Window#confirm(String)}) with the first warning message. If she
     * cancels, the current location will not change. Otherwise, the location changes and the new
     * place and all dependent places are created.
     *
     * @param newPlace a {@link SlottedPlace} instance
     */
    public void goTo(Place newPlace) {
        SlottedPlace slottedPlace;
        if (newPlace instanceof SlottedPlace) {
            slottedPlace = (SlottedPlace) newPlace;
        } else {
            if (legacyActivityMapper == null) {
                throw new IllegalStateException(
                        "Must use SlottedPlace unless LegacyActivityMapper is set");
            }

            slottedPlace = new WrappedPlace(newPlace, legacyActivityMapper);
        }
        goTo(slottedPlace, new SlottedPlace[0]);
    }

    /**
     * Same as {@link #goTo(Place)} except adds the ability to override default places for
     * any of the slots that will be created by the newPlace.
     *
     * @param nonDefaultPlaces array of {@link SlottedPlace}s that should be used instead of the
     * default places defined for the slots.
     */
    public void goTo(SlottedPlace newPlace, SlottedPlace... nonDefaultPlaces) {
        goTo(newPlace, nonDefaultPlaces, reloadAll);
    }

    /**
     * Same as {@link #goTo(SlottedPlace, SlottedPlace...)} except adds the ability to override
     * whether the existing {@link SlottedActivity}s should be refreshed.  The default is that all
     * pages are refreshed, and this method should only be used if you don't want to refresh
     * existing activities.
     *
     * @param reloadAll true if existing activities should be refreshed.
     */
    public void goTo(SlottedPlace newPlace, SlottedPlace[] nonDefaultPlaces, boolean reloadAll) {
        try {
            if (goToCount++ > 10) {
                throw new IllegalStateException("Goto appears to be in an infinite loop.");
            }

            currentParameters = new PlaceParameters();

            ArrayList<SlottedPlace> completeNonDefaults = new ArrayList<SlottedPlace>();
            completeNonDefaults.add(newPlace);
            Collections.addAll(completeNonDefaults, nonDefaultPlaces);

            if (navigationOverride != null) {
                completeNonDefaults = navigationOverride.checkOverrides(completeNonDefaults);
                newPlace = completeNonDefaults.get(0);
            }

            ActiveSlot slotToUpdate = root.findSlot(newPlace.getParentSlot());

            if (slotToUpdate == null) {
                addParents(newPlace, completeNonDefaults);
                slotToUpdate = root;
            }
            ArrayList<String> warnings = new ArrayList<String>();
            slotToUpdate.maybeGoTo(warnings);

            if (warnings.isEmpty() ||
                    delegate.confirm(warnings.toArray(new String[warnings.size()]))) {
                if (reloadAll) {
                    slotToUpdate = getRootAddNonDefaults(slotToUpdate, completeNonDefaults);
                }
                slotToUpdate.constructStopStart(currentParameters, completeNonDefaults, reloadAll);

                LinkedList<SlottedPlace> places = new LinkedList<SlottedPlace>();
                fillPlaces(root, places);

                historyMapper.createToken();

                eventBus.fireEvent(new NewPlaceEvent(places));
            }

            goToCount--;
        } catch (Exception e) {
            e.printStackTrace();
            log.log(Level.SEVERE, "Problem while goTo:" + newPlace, e);
        }
    }

    private void fillPlaces(ActiveSlot slot, LinkedList<SlottedPlace> places) {
        places.add(slot.getPlace());
        for (ActiveSlot childSlot : slot.getChildren()) {
            fillPlaces(childSlot, places);
        }
    }

    //todo javadoc
    public String createUrl(SlottedPlace newPlace) {
        String url = Document.get().getURL();
        String[] splitUrl = url.split("#");
        String token = createToken(newPlace);

        return splitUrl[0] + "#" + token;
    }

    //todo javadoc
    public String createToken(SlottedPlace newPlace) {
        PlaceParameters placeParameters = new PlaceParameters();
        newPlace.storeParameters(placeParameters);
        String token = historyMapper.createToken(newPlace, placeParameters);
        return token;
    }

    private void addParents(SlottedPlace newPlace, ArrayList<SlottedPlace> completeNonDefaults) {
        SlottedPlace parent = newPlace;
        while (parent.getParentSlot() != null && parent.getParentSlot().getOwnerPlace() != null) {
            parent = parent.getParentSlot().getOwnerPlace();
            completeNonDefaults.add(parent);
        }
    }

    private ActiveSlot getRootAddNonDefaults(ActiveSlot baseSlot,
            ArrayList<SlottedPlace> nonDefaultPlaces)
    {
        ActiveSlot root = baseSlot;
        ActiveSlot parent = baseSlot.getParent();
        while (parent != null) {
            root = parent;
            nonDefaultPlaces.add(parent.getPlace());
            parent = parent.getParent();
        }

        return root;
    }

    /**
     * Returns the root {@link ActiveSlot}.
     *
     * @return a {@link ActiveSlot} instance.
     */
    public ActiveSlot getRoot() {
        return root;
    }

    /**
     * Returns the {@link PlaceParameters} that used to display the current state.
     *
     * @return a {@link PlaceParameters} instance.
     */
    public PlaceParameters getCurrentParameters() {
        return currentParameters;
    }

    /**
     * Returns the EventBus used for all events in the slotted framework.
     */
    public EventBus getEventBus() {
        return eventBus;
    }

    /**
     * Returns the GWT's ActivityMapper used to create Activities, or null if none was set.
     */
    public ActivityMapper getLegacyActivityMapper() {
        return legacyActivityMapper;
    }
}
