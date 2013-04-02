package com.googlecode.slotted.testharness.client.flow;

import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.testharness.client.TestPlace;

public class GParam1aPlace extends TestPlace {

    public GParam1aPlace() {
        setParameter("GParam1a", "set");
        setParameter("global", "GParam1a");
    }

    @Override public Slot getParentSlot() {
        return GParamPlace.Slot1;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }
}
