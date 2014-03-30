package com.googlecode.slotted.activity_cache.client;

import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedActivity;
import com.googlecode.slotted.client.SlottedPlace;

public class CachePlace extends SlottedPlace {
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
