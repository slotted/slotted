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

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.ResettableEventBus;
import com.googlecode.slotted.client.SlottedController.RootSlotImpl;

import java.util.ArrayList;
import java.util.Iterator;

//todo javadoc
public class ActiveSlot {
    /**
     * Wraps our real display to prevent an Activity from taking it over if it is not the
     * currentActivity.
     */
    private class ProtectedDisplay implements AcceptsOneWidget {
        private final Activity activity;
        private boolean loading = false;
        private IsWidget view;
        private boolean widgetShown = false;

        ProtectedDisplay(Activity activity) {
            this.activity = activity;
        }

        public void setWidget(IsWidget view) {
            this.view = view;
            if (!loading) {
                activityStarting = false;
            }
            if (widgetShown && this.activity == ActiveSlot.this.activity) {
                showWidget();
            }
        }

        private void showWidget() {
            widgetShown = true;
            if (view != null) {
                slot.getDisplay().setWidget(view);
                activityStarting = false;
            }
        }
    }

    private ActiveSlot parent;
    private ArrayList<ActiveSlot> children = new ArrayList<ActiveSlot>();
    private Slot slot;
    private SlottedPlace place;
    private SlottedPlace newPlace;
    private Activity activity;
    private boolean activityStarting;
    private ProtectedDisplay currentProtectedDisplay;
    private SlottedController slottedController;
    private HistoryMapper historyMapper;
    private ResettableEventBus resettableEventBus;

    public ActiveSlot(ActiveSlot parent, Slot slot, EventBus eventBus,
            SlottedController slottedController)
    {
        this.parent = parent;
        this.slot = slot;
        this.slottedController = slottedController;
        this.historyMapper = slottedController.getHistoryMapper();
        this.resettableEventBus = new ResettableEventBus(eventBus);
    }

    public void maybeGoTo(Iterable<SlottedPlace> nonDefaultPlaces, boolean reloadAll,
            ArrayList<String> warnings)
    {
        newPlace = getPlace(nonDefaultPlaces);
        if (reloadAll || !newPlace.equals(place)) {
            if (activity != null) {
                String warning = activity.mayStop();
                if (warning != null) {
                    warnings.add(warning);
                }
            }
            reloadAll = true;
        }
        if (children != null) {
            for (ActiveSlot child : children) {
                child.maybeGoTo(nonDefaultPlaces, reloadAll, warnings);
            }
        }
    }

    public ActiveSlot findSlot(Slot slotToFind) {
        ActiveSlot found = null;
        if (slotToFind == null) {
            found = null;
        } else if (slotToFind.equals(slot)) {
            found = this;
        } else if (children != null) {
            Iterator<ActiveSlot> childIt = children.iterator();
            while (found == null && childIt.hasNext()) {
                ActiveSlot child = childIt.next();
                found = child.findSlot(slotToFind);
            }
        }
        return found;
    }

    public void stopActivities() {
        try {
            place = null;
            if (activity != null) {
                if (activityStarting) {
                    activity.onCancel();
                } else {
                    activity.onStop();
                }
                activity = null;
                activityStarting = false;
            }
            if (children != null) {
                for (ActiveSlot child : children) {
                    child.stopActivities();
                }
                children.clear();
            }
            currentProtectedDisplay = null;
        } finally {
            resettableEventBus.removeHandlers();
        }
    }

    public void constructStopStart(PlaceParameters parameters,
            Iterable<SlottedPlace> nonDefaultPlaces, boolean reloadAll)
    {
        if (newPlace == null) {
            newPlace = getPlace(nonDefaultPlaces);
        }
        historyMapper.extractParameters(newPlace, parameters);
        newPlace.setPlaceParameters(parameters);

        if (reloadAll || !newPlace.equals(place)) {
            stopActivities();
        }
        place = newPlace;
        newPlace = null;

        createChildren();

        if (slottedController.shouldStartActivity()) {
            if (activity == null) {
                getStartActivity(parameters);
            } else {
                refreshActivity(parameters);
            }
        }

        for (ActiveSlot child : children) {
            child.constructStopStart(parameters, nonDefaultPlaces, reloadAll);
        }
    }

    private SlottedPlace getPlace(Iterable<SlottedPlace> nonDefaultPlaces) {
        for (SlottedPlace place : nonDefaultPlaces) {
            boolean isRootPlace =
                    place.getParentSlot() == null || place.getParentSlot() instanceof RootSlotImpl;
            boolean isRoot = slot.getOwnerPlace() == null;
            if (isRootPlace && isRoot) {
                return place;
            }
            if (slot.equals(place.getParentSlot())) {
                return place;
            }
        }
        if (place != null) {
            return place;
        }
        return slot.getDefaultPlace();
    }

    private void getStartActivity(PlaceParameters parameters) {
        activity = place.getActivity();
        if (activity == null) {
            ActivityMapper mapper = slottedController.getLegacyActivityMapper();
            if (mapper == null) {
                throw new IllegalStateException("SlottedPlace.getActivity() returned null, " +
                        "and LegacyActivityMapper wasn't set.");
            }
            activity = mapper.getActivity(place);
            if (activity == null) {
                throw new IllegalStateException("SlottedPlace.getActivity() returned null, " +
                        "and LegacyActivityMapper also return null.");
            }
        }
        if (activity instanceof SlottedActivity) {
            ((SlottedActivity) activity).init(slottedController, place, parameters,
                    resettableEventBus, this);
        }
        com.google.gwt.event.shared.ResettableEventBus legacyBus =
                new com.google.gwt.event.shared.ResettableEventBus(resettableEventBus);
        activityStarting = true;
        currentProtectedDisplay = new ProtectedDisplay(activity);
        activity.start(currentProtectedDisplay, legacyBus);

        if (activity instanceof SlottedActivity) {
            for (ActiveSlot child: children) {
                Slot slot = child.getSlot();
                ((SlottedActivity) activity).setChildSlotDisplay(slot);
                if (slot.getDisplay() == null) {
                    throw new IllegalStateException(activity + " didn't correctly set the display for a Slot.");
                }
            }
        } else if (!children.isEmpty()) {
            throw new IllegalStateException(place + " needs to create a SlottedActivity, because " +
                    "it has child slots.");
        }
    }

    private void refreshActivity(PlaceParameters parameters) {
        if (activity instanceof SlottedActivity) {
            SlottedActivity slottedActivity = (SlottedActivity) activity;
            slottedActivity.init(slottedController, place, parameters,
                    resettableEventBus, this);
            slottedActivity.onRefresh();
        }
    }

    private void createChildren() {
        Slot[] childSlots = place.getChildSlots();
        if (childSlots != null && childSlots.length > 0 && children.isEmpty()) {
            for (Slot child: childSlots) {
                ActiveSlot activeSlot =  new ActiveSlot(this, child, resettableEventBus,
                        slottedController);
                children.add(activeSlot);
            }
            assert childSlots.length == children.size() : "Error creating children ActiveSlots";
        }
    }

    public void setLoading(boolean loading) {
        if (currentProtectedDisplay == null) {
            throw new IllegalStateException("Attempting to set loading before activity started.");
        }
        currentProtectedDisplay.loading = loading;
        if (!loading) {
            slottedController.attemptShowViews();
        }
    }

    public SlottedPlace getFirstLoadingPlace() {
        if (currentProtectedDisplay == null || currentProtectedDisplay.loading) {
            return place;
        }
        if (children!= null) {
            for (ActiveSlot child: children) {
                SlottedPlace childPlace = child.getFirstLoadingPlace();
                if (childPlace != null) {
                    return childPlace;
                }
            }
        }
        return null;
    }

    public void showViews() {
        if (currentProtectedDisplay == null || currentProtectedDisplay.loading) {
            throw new IllegalStateException("Attempting to show a view for a loading slot:" + place);
        }
        currentProtectedDisplay.showWidget();
        if (children!= null) {
            for (ActiveSlot child: children) {
                child.showViews();
            }
        }
    }

    public ActiveSlot getParent() {
        return parent;
    }

    public ArrayList<ActiveSlot> getChildren() {
        return children;
    }

    public Slot getSlot() {
        return slot;
    }

    public EventBus getEventBus() {
        return resettableEventBus;
    }

    public SlottedPlace getPlace() {
        return place;
    }

    public Activity getActivity() {
        return activity;
    }

}
