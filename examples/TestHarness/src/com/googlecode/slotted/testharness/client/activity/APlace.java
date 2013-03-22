package com.googlecode.slotted.testharness.client.activity;

import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedController;

public class APlace extends TestPlace {
    public static final Slot SLOT = new Slot(new APlace(), new A1aPlace());

    public APlace() {
        super();
    }

    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {SLOT};
    }
}
