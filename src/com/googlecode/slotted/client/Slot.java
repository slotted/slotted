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

import com.google.gwt.user.client.ui.AcceptsOneWidget;

/**
 * Object definition of a Slot in the Place hierarchy.  The definition should be a static instance
 * in the SlottedPlace class, and is returned by {@link SlottedPlace#getParentSlot()} and
 * {@link SlottedPlace#getChildSlots()}.
 */
public class Slot {
    private SlottedPlace ownerPlace;
    private SlottedPlace defaultPlace;
    private AcceptsOneWidget display;

    /**
     * Create a Slot definition.
     *
     * @param ownerPlace The SlottedPlace that is responsible for Displaying this Slot.  This Place
     *                   will be used when the parent is not specified in the SlottedController#goTo(),
     *                   so it should have the correct parameters set.
     * @param defaultPlace The SlottedPlace that should be displayed, when a SlottedController#goTo()
     *                     doesn't specify which Place should be displayed for this Slot.  This should
     *                     have the correct parameters set.
     */
    public Slot(SlottedPlace ownerPlace, SlottedPlace defaultPlace) {
        this.ownerPlace = ownerPlace;
        this.defaultPlace = defaultPlace;
    }

    /**
     * Gets the SlottedPlace that is responsible for Displaying this Slot.
     */
    public SlottedPlace getOwnerPlace() {
        return ownerPlace;
    }

    /**
     * Gets the SlottedPlace that should be displayed, when a SlottedController#goTo()
     * doesn't specify which Place should be displayed for this Slot.
     */
    public SlottedPlace getDefaultPlace() {
        return defaultPlace;
    }

    /**
     * @deprecated
     * This is now handled by {@link SlottedActivity#getChildSlotDisplay(Slot)}
     */
    public void setDisplay(AcceptsOneWidget display) {
        if (display == null) {
            throw new NullPointerException("Display can't be null.");
        }
        this.display = display;
    }

    /**
     * Only used by the SlottedController for backwards compatibility.
     */
    protected AcceptsOneWidget getDisplay() {
        return display;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Slot)) {
            return false;
        }

        Slot slot = (Slot) o;

        if (defaultPlace != null ? !defaultPlace.equals(slot.defaultPlace) :
                slot.defaultPlace != null) {
            return false;
        }
        if (ownerPlace != null ? !ownerPlace.equals(slot.ownerPlace) : slot.ownerPlace != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = ownerPlace != null ? ownerPlace.hashCode() : 0;
        result = 31 * result + (defaultPlace != null ? defaultPlace.hashCode() : 0);
        return result;
    }
}
