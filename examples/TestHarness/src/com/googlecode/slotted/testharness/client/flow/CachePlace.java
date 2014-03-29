package com.googlecode.slotted.testharness.client.flow;

import com.googlecode.slotted.client.CacheActivities;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedController;
import com.googlecode.slotted.testharness.client.TestPlace;

@CacheActivities(CacheAPlace.class)
public class CachePlace extends TestPlace {
    public static final Slot SLOT = new Slot(new CachePlace(), new CacheAPlace(1));

    public CachePlace() {
        super();
    }

    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {SLOT};
    }
}
