package com.googlecode.slotted.testharness.client.multi_parent;

import com.googlecode.slotted.client.MultiParentPlace;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedController;

public class MultiPlace extends MultiParentPlace {
    public static Slot slot = new Slot(new MultiPlace(), new Child1Place());

    public MultiPlace() {
    }

    public MultiPlace(Slot parentSlot) {
        super(parentSlot);
    }

    @Override public Slot[] getParentSlots() {
        return new Slot[] {Parent1Place.slot, Parent2Place.slot, SlottedController.RootSlot};
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {slot};
    }
}
