package com.googlecode.slotted.testharness.client.flow;

import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.testharness.client.TestPlace;

public class B1aPlace extends TestPlace {
    @Override public Slot getParentSlot() {
        return BPlace.Slot1;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }
}
