package com.googlecode.slotted.testharness.client.multi_parent;

import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.testharness.client.TestPlace;

public class Parent2Child1Place extends TestPlace {
    @Override public Slot getParentSlot() {
        return Parent2Place.slot;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }
}
