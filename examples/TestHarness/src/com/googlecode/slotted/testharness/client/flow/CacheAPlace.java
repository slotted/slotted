package com.googlecode.slotted.testharness.client.flow;

import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.TokenizerParameter;
import com.googlecode.slotted.testharness.client.TestPlace;

public class CacheAPlace extends TestPlace {
    public static final Slot SLOT = new Slot(new CacheAPlace(), new CacheA1aPlace());

    @TokenizerParameter
    public int a;

    private CacheAPlace() {
    }

    public CacheAPlace(int a) {
        this.a = a;
    }

    @Override public Slot getParentSlot() {
        return CachePlace.SLOT;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {SLOT};
    }
}
