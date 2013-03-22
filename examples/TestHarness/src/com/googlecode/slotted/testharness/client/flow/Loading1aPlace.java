package com.googlecode.slotted.testharness.client.flow;

import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.testharness.client.TestPlace;

public class Loading1aPlace extends TestPlace {
    @Override public Slot getParentSlot() {
        return LoadingPlace.Slot1;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }
}
