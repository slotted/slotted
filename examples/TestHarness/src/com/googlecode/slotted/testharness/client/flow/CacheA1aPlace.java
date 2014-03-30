package com.googlecode.slotted.testharness.client.flow;

import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.testharness.client.TestPlace;

public class CacheA1aPlace extends TestPlace {
    public CacheA1aPlace() {
        super();
    }

    @Override public Slot getParentSlot() {
        return CacheAPlace.SLOT;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }
}
