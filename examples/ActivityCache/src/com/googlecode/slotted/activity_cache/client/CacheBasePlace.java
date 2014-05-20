package com.googlecode.slotted.activity_cache.client;

import com.googlecode.slotted.client.CacheActivities;
import com.googlecode.slotted.client.ContainerPlace;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedController;

@CacheActivities(CachePlace.class)
public class CacheBasePlace extends ContainerPlace {
    public static Slot SLOT = new Slot(new CacheBasePlace(), new CachePlace());

    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[]{SLOT};
    }
}
