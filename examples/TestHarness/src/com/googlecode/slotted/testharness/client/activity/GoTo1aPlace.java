package com.googlecode.slotted.testharness.client.activity;

import com.googlecode.slotted.client.Slot;

public class GoTo1aPlace extends TestPlace {
    @Override public Slot getParentSlot() {
        return GoToPlace.Slot1;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }
}
