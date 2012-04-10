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

public class Slot {
    private SlottedPlace parentPlace;
    private SlottedPlace defaultPlace;
    private AcceptsOneWidget display;

    public Slot(SlottedPlace parentPlace, SlottedPlace defaultPlace) {
        this.parentPlace = parentPlace;
        this.defaultPlace = defaultPlace;
    }

    public SlottedPlace getParentPlace() {
        return parentPlace;
    }

    public void setParentPlace(SlottedPlace parentPlace) {
        this.parentPlace = parentPlace;
    }

    public SlottedPlace getDefaultPlace() {
        return defaultPlace;
    }

    public void setDefaultPlace(SlottedPlace defaultPlace) {
        this.defaultPlace = defaultPlace;
    }

    public void setDisplay(AcceptsOneWidget display) {
        this.display = display;
    }

    public AcceptsOneWidget getDisplay() {
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

        if (defaultPlace != null ? !defaultPlace.equals(slot.defaultPlace) : slot.defaultPlace != null) {
            return false;
        }
        if (parentPlace != null ? !parentPlace.equals(slot.parentPlace) : slot.parentPlace != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = parentPlace != null ? parentPlace.hashCode() : 0;
        result = 31 * result + (defaultPlace != null ? defaultPlace.hashCode() : 0);
        return result;
    }
}
