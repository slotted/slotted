package com.googlecode.slotted.testharness.client.multi_parent;

import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.testharness.client.TestPlace;

public class Parent1Child1Place extends TestPlace {
    @Override public Slot getParentSlot() {
        return Parent1Place.slot;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }
}
