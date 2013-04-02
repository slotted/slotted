package com.googlecode.slotted.testharness.client.flow;

import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedController;
import com.googlecode.slotted.testharness.client.TestPlace;

public class GParamPlace extends TestPlace {
    public static final Slot Slot1 = new Slot(new GParamPlace(), new GParam1aPlace());
    public static final Slot Slot2 = new Slot(new GParamPlace(), new GParam2aPlace());

    public GParamPlace() {
        setParameter("GParam", "set");
        setParameter("global", "GParam");
    }

    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {Slot1, Slot2};
    }
}
