package com.googlecode.slotted.testharness.client.multi_parent;

import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedController;
import com.googlecode.slotted.testharness.client.TestPlace;

public class Parent1Place extends TestPlace {
    public static Slot slot = new Slot(new Parent1Place(), new MultiPlace());

    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {slot};
    }
}
