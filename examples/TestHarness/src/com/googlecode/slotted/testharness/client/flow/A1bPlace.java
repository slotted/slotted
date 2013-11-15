package com.googlecode.slotted.testharness.client.flow;

import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.testharness.client.TestPlace;

public class A1bPlace extends TestPlace {
    public static final Slot SLOT = new Slot(new A1bPlace(), new A1b1aPlace());

    @Override public Slot getParentSlot() {
        return APlace.SLOT;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {SLOT};
    }
}
