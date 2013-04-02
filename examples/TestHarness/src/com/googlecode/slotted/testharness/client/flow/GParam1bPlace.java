package com.googlecode.slotted.testharness.client.flow;

import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.testharness.client.TestPlace;

public class GParam1bPlace extends TestPlace {

    public GParam1bPlace() {
        setParameter("GParam1b", "set");
        setParameter("global", "GParam1b");
    }

    @Override public Slot getParentSlot() {
        return GParamPlace.Slot1;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }
}
