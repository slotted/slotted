package com.googlecode.slotted.testharness.client.activity;

import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedController;

public class BPlace extends TestPlace {
    public static final Slot Slot1 = new Slot(new BPlace(), new B1aPlace());
    public static final Slot Slot2 = new Slot(new BPlace(), new B2aPlace());

    public BPlace() {
        super();
    }

    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {Slot1, Slot2};
    }
}
