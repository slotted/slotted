package com.npc.slotted.example.client;

import com.googlecode.slotted.client.MappedSlottedPlace;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedController;

public class BasePlace extends MappedSlottedPlace {
    public static final Slot SLOT = new Slot(new BasePlace(), new HelloPlace("Base!"));

    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {SLOT};
    }
}
