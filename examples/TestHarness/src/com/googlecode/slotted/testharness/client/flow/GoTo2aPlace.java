package com.googlecode.slotted.testharness.client.flow;

import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.testharness.client.TestPlace;

public class GoTo2aPlace extends TestPlace {
    @Override public Slot getParentSlot() {
        return GoToPlace.Slot2;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }
}
