package com.googlecode.slotted.testharness.client.activity;

import com.googlecode.slotted.client.Slot;

public class B2aPlace extends TestPlace {
    @Override public Slot getParentSlot() {
        return BPlace.Slot2;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }
}
