/*
 * Copyright 2012 NPC Unlimited
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
package org.npc.slotted.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Logger;

/**
 * Controls display of the RootSlot and all nested slots.
 */
public class SlottedController {
    /**
     * RootSlot instance that should be used to define {@link SlottedPlace}s that should be displayed
     * in the root slot.
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
     * Optional delegate in charge of Window-related events. Provides nice
     * isolation for unit testing, and allows customization of confirmation
     * handling.
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

    private SlottedPlace defaultPlace;
    private final EventBus eventBus;
    private final HistoryMapper historyMapper;

    private final Delegate delegate;
    private ActiveSlot root;
    private PlaceParameters currentParameters;

    /**
     * Create a new SlottedController with a {@link DefaultDelegate}. The
     * DefaultDelegate is created via a call to GWT.create(), so an alternative
     * default implementation can be provided through &lt;replace-with&gt; rules
     * in a {@code .gwt.xml} file.
     *
     * @param defaultPlace  the place that should be displayed if root slot is defined.
     * @param historyMapper the {@link HistoryMapper}
     * @param eventBus      the {@link EventBus}
     */
    public SlottedController(SlottedPlace defaultPlace, HistoryMapper historyMapper, EventBus eventBus) {
        this(defaultPlace, historyMapper, eventBus, (Delegate) GWT.create(DefaultDelegate.class));
    }

    /**
     * Create a new SlottedController.
     *
     * @param defaultPlace  the place that should be displayed if root slot is defined.
     * @param historyMapper the {@link HistoryMapper}
     * @param eventBus      the {@link EventBus}
     * @param delegate      the {@link Delegate} in charge of Window-related events
     */
    public SlottedController(SlottedPlace defaultPlace, HistoryMapper historyMapper, EventBus eventBus,
            Delegate delegate) {
        this.defaultPlace = defaultPlace;
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

    /**
     * Sets the widget that displays the root slot content.  This must be set before any content can be displayed.
     *
     * @param display an instance of AcceptsOneWidget
     */
    public void setDisplay(AcceptsOneWidget display) {
        Slot rootSlot = new RootSlotImpl(defaultPlace);
        rootSlot.setDisplay(display);
        root = new ActiveSlot(null, rootSlot, eventBus);

        History.fireCurrentHistoryState();
    }

    /**
     * Used by the HistoryMapper to display the default place.  This method works as if you called
     * {@link #goTo(SlottedPlace, PlaceParameters)} with the default place.
     */
    protected void goToDefaultPlace() {
        goTo(defaultPlace, new PlaceParameters(), new SlottedPlace[0], true);
    }

    /**
     * Request a change to a new place. It is not a given that we'll actually get
     * there. First all active {@link SlottedActivity#mayStop()} will called.
     * If any activities return a warning message, it will be
     * presented to the user via {@link Delegate#confirm(String[])} (which is
     * typically a call to {@link Window#confirm(String)}) with the first warning
     * message. If she cancels, the current location will not change. Otherwise,
     * the location changes and the new place and all dependent places are created.
     *
     * @param newPlace   a {@link SlottedPlace} instance
     * @param parameters a {@link PlaceParameters} instance with all the parameters to
     *                   display all slots.
     */
    public void goTo(SlottedPlace newPlace, PlaceParameters parameters) {
        goTo(newPlace, parameters, new SlottedPlace[0], true);
    }

    /**
     * Same as {@link #goTo(SlottedPlace, PlaceParameters)} creates and empty PlaceParameters.
     */
    public void goTo(SlottedPlace newPlace) {
        goTo(newPlace, new PlaceParameters());
    }

    /**
     * Same as {@link #goTo(SlottedPlace, PlaceParameters)} except adds the ability to
     * override default places for any of the slots that will be created by the newPlace.
     *
     * @param nonDefaultPlaces array of {@link SlottedPlace}s that should be used instead of the default
     *                         places defined for the slots.
     */
    public void goTo(SlottedPlace newPlace, PlaceParameters parameters, SlottedPlace... nonDefaultPlaces) {
        goTo(newPlace, parameters, nonDefaultPlaces, true);
    }

    /**
     * Same as {@link #goTo(SlottedPlace, PlaceParameters, SlottedPlace...)} except adds the ability to
     * override whether the existing {@link SlottedActivity}s should be refreshed.  The default is that
     * all pages are refreshed, and this method should only be used if you don't want to refresh existing
     * activities.
     *
     * @param refreshAll true if existing activities should be refreshed.
     */
    public void goTo(SlottedPlace newPlace, PlaceParameters parameters,
            SlottedPlace[] nonDefaultPlaces, boolean refreshAll) {
        currentParameters = parameters;

        ArrayList<SlottedPlace> completeNonDefaults = new ArrayList<SlottedPlace>();
        completeNonDefaults.add(newPlace);
        Collections.addAll(completeNonDefaults, nonDefaultPlaces);

        ActiveSlot slotToUpdate = root.findSlot(newPlace.getSlot());

        if (slotToUpdate == null) {
            addParents(newPlace, completeNonDefaults);
            slotToUpdate = root;
        }
        ArrayList<String> warnings = new ArrayList<String>();
        slotToUpdate.maybeGoTo(warnings);

        if (warnings.isEmpty() || delegate.confirm(warnings.toArray(new String[warnings.size()]))) {
            slotToUpdate.stopActivities();
            if (refreshAll) {
                slotToUpdate = getRootAddNonDefaults(slotToUpdate, completeNonDefaults);
            }
            slotToUpdate.constructAndStart(parameters, completeNonDefaults);

            historyMapper.createToken();
        }
    }

      //todo javadoc
    public String createUrl(SlottedPlace newPlace, PlaceParameters parameters) {
        String url = Document.get().getURL();
        String[] splitUrl = url.split("#");
        String token = historyMapper.createToken(newPlace, parameters);

        return splitUrl[0] + "#" + token;
    }

    //todo javadoc
    public String createToken(SlottedPlace newPlace, PlaceParameters parameters) {
        return historyMapper.createToken(newPlace, parameters);
    }

    private void addParents(SlottedPlace newPlace, ArrayList<SlottedPlace> completeNonDefaults) {
        SlottedPlace parent = newPlace;
        while (parent.getSlot() != null && parent.getSlot().getParentPlace() != null) {
            parent = parent.getSlot().getParentPlace();
            completeNonDefaults.add(parent);
        }
    }

    private ActiveSlot getRootAddNonDefaults(ActiveSlot baseSlot, ArrayList<SlottedPlace> nonDefaultPlaces) {
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
}
