package com.googlecode.slotted.testharness.client.activity;

import com.googlecode.slotted.client.Slot;

public class A1a1aPlace extends TestPlace {
    @Override public Slot getParentSlot() {
        return A1aPlace.SLOT;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }
}
