package com.googlecode.slotted.testharness.client.activity;

import com.googlecode.slotted.client.Slot;

public class B1bPlace extends TestPlace {
    @Override public Slot getParentSlot() {
        return BPlace.Slot1;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }
}
