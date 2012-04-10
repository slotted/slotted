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

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.EventBus;
import org.npc.slotted.client.SlottedController.RootSlotImpl;

import java.util.ArrayList;
import java.util.Iterator;

public class ActiveSlot {
    /**
     * Wraps our real display to prevent an Activity from taking it over if it is
     * not the currentActivity.
     */
    private class ProtectedDisplay implements AcceptsOneWidget {
        private final SlottedActivity activity;

        ProtectedDisplay(SlottedActivity activity) {
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
    private EventBus eventBus;
    private SlottedPlace place;
    private SlottedActivity activity;

    public ActiveSlot(ActiveSlot parent, Slot slot, EventBus eventBus) {
        this.parent = parent;
        this.slot = slot;
        this.eventBus = eventBus;
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
            for (ActiveSlot child: children) {
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
        if (activity != null) {
            activity.onStop();
        }
        if (children != null) {
            for (ActiveSlot child: children) {
                child.stopActivities();
            }
        }
    }

    public void constructAndStart(PlaceParameters parameters, Iterable<SlottedPlace> nonDefaultPlaces) {
        SlottedPlace newPlace = getPlace(nonDefaultPlaces);
        if (newPlace.equals(place)) {
            activity.refresh(new ProtectedDisplay(activity), parameters, eventBus);
        } else {
            place = newPlace;
            activity = place.getActivity();
            activity.start(slot.getDisplay(), parameters, eventBus);
            createChildren();
        }

        for (ActiveSlot child: children) {
            child.constructAndStart(parameters, nonDefaultPlaces);
        }
    }

    private SlottedPlace getPlace(Iterable<SlottedPlace> nonDefaultPlaces) {
        for (SlottedPlace place: nonDefaultPlaces) {
            boolean isRootPlace = place.getSlot() == null || place.getSlot() instanceof RootSlotImpl;
            boolean isRoot = slot.getParentPlace() == null;
            if (isRootPlace && isRoot) {
                return place;
            }
            if (slot.equals(place.getSlot())) {
                return place;
            }
        }
        return slot.getDefaultPlace();
    }

    private void createChildren() {
        children = new ArrayList<ActiveSlot>();
        if (activity != null) {
            Slot[] childSlots = activity.getChildSlots();
            if (childSlots != null ) {
                for (Slot slot: childSlots) {
                    if (slot.getDisplay() == null || slot.getParentPlace() == null ||  slot.getDefaultPlace() == null) {
                        //todo better error message
                        throw new IllegalStateException("Slot must have ParentPlace, DefaultPlace, and Display");
                    }
                    ActiveSlot child = new ActiveSlot(this, slot, eventBus);
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
        return eventBus;
    }

    public SlottedPlace getPlace() {
        return place;
    }

    public SlottedActivity getActivity() {
        return activity;
    }

}
