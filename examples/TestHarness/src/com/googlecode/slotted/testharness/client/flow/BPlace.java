package com.googlecode.slotted.testharness.client.flow;

import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedController;
import com.googlecode.slotted.client.TokenizerParameter;
import com.googlecode.slotted.testharness.client.TestPlace;

public class BPlace extends TestPlace {
    public static final Slot Slot1 = new Slot(new BPlace(), new B1aPlace());
    public static final Slot Slot2 = new Slot(new BPlace(), new B2aPlace());

    @TokenizerParameter
    private int value;

    public BPlace() {
        value = 0;
    }

    public BPlace(int value) {
        this.value = value;
    }

    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {Slot1, Slot2};
    }
}
