package com.googlecode.slotted.testharness.client.flow;

import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedController;
import com.googlecode.slotted.testharness.client.TestPlace;

public class LoadingPlace extends TestPlace {
    public static final Slot Slot1 = new Slot(new LoadingPlace(), new Loading1aPlace());

    public LoadingPlace() {
        super();
    }

    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {Slot1};
    }
}
