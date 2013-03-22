package com.googlecode.slotted.testharness.client.flow;

import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.testharness.client.TestPlace;

public class B2aPlace extends TestPlace {
    @Override public Slot getParentSlot() {
        return BPlace.Slot2;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }
}
