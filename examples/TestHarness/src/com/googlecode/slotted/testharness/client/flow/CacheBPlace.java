package com.googlecode.slotted.testharness.client.flow;

import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.testharness.client.TestPlace;

public class CacheBPlace extends TestPlace {
    public CacheBPlace() {
        super();
    }

    @Override public Slot getParentSlot() {
        return CachePlace.SLOT;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }
}
