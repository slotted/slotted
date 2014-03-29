package com.googlecode.slotted.activity_cache.client;

import com.googlecode.slotted.client.CacheActivities;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedActivity;
import com.googlecode.slotted.client.SlottedController;
import com.googlecode.slotted.client.SlottedPlace;

@CacheActivities(CachePlace.class)
public class CacheBasePlace extends SlottedPlace {
    public static Slot SLOT = new Slot(new CacheBasePlace(), new CachePlace());

    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[]{SLOT};
    }

    @Override
    public SlottedActivity getActivity() {
        return new CacheBaseActivity();
    }
}
