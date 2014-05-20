package com.googlecode.slotted.activity_cache.client;

import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedActivity;
import com.googlecode.slotted.client.SlottedPlace;
import com.googlecode.slotted.client.TokenizerParameter;

public class CachePlace extends SlottedPlace {
    @TokenizerParameter
    private boolean flag;

    public CachePlace() {
    }

    public CachePlace(boolean flag) {
        this.flag = flag;
    }

    public boolean getFlag() {
        return flag;
    }

    @Override public Slot getParentSlot() {
        return CacheBasePlace.SLOT;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }

    @Override
    public SlottedActivity getActivity() {
        return new CacheActivity();
    }
}
