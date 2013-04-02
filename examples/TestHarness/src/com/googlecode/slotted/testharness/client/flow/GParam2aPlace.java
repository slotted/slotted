package com.googlecode.slotted.testharness.client.flow;

import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.testharness.client.TestPlace;

public class GParam2aPlace extends TestPlace {
    public GParam2aPlace() {
        setParameter("GParam2a", "set");
        setParameter("global", "GParam2a");
    }

    @Override public Slot getParentSlot() {
        return GParamPlace.Slot2;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }
}
