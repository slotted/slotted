package com.googlecode.slotted.testharness.client.flow;

import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.testharness.client.TestPlace;

public class A1b1aPlace extends TestPlace {
    @Override public Slot getParentSlot() {
        return A1bPlace.SLOT;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }
}
