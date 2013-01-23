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

public class ActiveSlot {
    /**
     * Wraps our real display to prevent an Activity from taking it over if it is not the
     * currentActivity.
     */
    private class ProtectedDisplay implements AcceptsOneWidget {
        private final Activity activity;

        ProtectedDisplay(Activity activity) {
            this.activity = activity;
        }

        public void setWidget(IsWidget view) {
            if (this.activity == ActiveSlot.this.activity) {
                showWidget(view);
            }
        }
    }

    private ActiveSlot parent;
    private ArrayList<ActiveSlot> children;
    private Slot slot;
    private SlottedController slottedController;
    private ResettableEventBus resettableEventBus;
    private SlottedPlace place;
    private Activity activity;

    public ActiveSlot(ActiveSlot parent, Slot slot, EventBus eventBus,
            SlottedController slottedController)
    {
        this.parent = parent;
        this.slot = slot;
        this.slottedController = slottedController;
        this.resettableEventBus = new ResettableEventBus(eventBus);

        children = new ArrayList<ActiveSlot>();
    }

    private void showWidget(IsWidget view) {
        if (slot.getDisplay() != null) {
            slot.getDisplay().setWidget(view);
        }
    }

    public void maybeGoTo(ArrayList<String> warnings) {
        if (activity != null) {
            String warning = activity.mayStop();
            if (warning != null) {
                warnings.add(warning);
            }
        }
        if (children != null) {
            for (ActiveSlot child : children) {
                child.maybeGoTo(warnings);
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
        resettableEventBus.removeHandlers();
        if (activity != null) {
            activity.onStop();
        }
        if (children != null) {
            for (ActiveSlot child : children) {
                child.stopActivities();
            }
        }
    }

    public void constructStopStart(PlaceParameters parameters,
            Iterable<SlottedPlace> nonDefaultPlaces, boolean refreshAll)
    {
        SlottedPlace newPlace = getPlace(nonDefaultPlaces);
        newPlace.storeParameters(parameters);
        if (refreshAll || !newPlace.equals(place)) {
            stopActivities();
            place = newPlace;
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
                        resettableEventBus);
            }
            com.google.gwt.event.shared.ResettableEventBus legacyBus = new com.google.gwt.event
                    .shared.ResettableEventBus(
                    resettableEventBus);
            activity.start(new ProtectedDisplay(activity), legacyBus);
            createChildren();
        }

        for (ActiveSlot child : children) {
            child.constructStopStart(parameters, nonDefaultPlaces, refreshAll);
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
        return slot.getDefaultPlace();
    }

    private void createChildren() {
        children = new ArrayList<ActiveSlot>();
        if (activity != null && activity instanceof SlottedActivity) {
            SlottedActivity slottedActivity = (SlottedActivity) activity;
            Slot[] childSlots = place.getChildSlots();
            if (childSlots != null) {
                for (Slot slot : childSlots) {
                    slottedActivity.setChildSlotDisplay(slot);
                    if (slot.getDisplay() == null || slot.getOwnerPlace() == null ||
                            slot.getDefaultPlace() == null) {
                        //todo better error message
                        throw new IllegalStateException(
                                "Slot must have ParentPlace, DefaultPlace, and Display");
                    }
                    ActiveSlot child = new ActiveSlot(this, slot, resettableEventBus,
                            slottedController);
                    children.add(child);
                }
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
