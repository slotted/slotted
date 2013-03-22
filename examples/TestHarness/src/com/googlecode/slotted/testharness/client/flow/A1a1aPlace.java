package com.googlecode.slotted.testharness.client.flow;

import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.testharness.client.TestPlace;

public class A1a1aPlace extends TestPlace {
    @Override public Slot getParentSlot() {
        return A1aPlace.SLOT;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }
}
