package com.googlecode.slotted.testharness.client.activity;

import com.googlecode.slotted.client.Slot;

public class A1aPlace extends TestPlace {
    public static final Slot SLOT = new Slot(new A1aPlace(), new A1a1aPlace());

    @Override public Slot getParentSlot() {
        return APlace.SLOT;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {SLOT};
    }
}
