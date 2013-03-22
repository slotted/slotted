package com.googlecode.slotted.testharness.client.activity;

import com.googlecode.slotted.client.Slot;

public class GoTo2bPlace extends TestPlace {
    @Override public Slot getParentSlot() {
        return GoToPlace.Slot2;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }
}
