package com.npc.slotted.example.client;

import org.npc.slotted.client.MappedSlottedPlace;
import org.npc.slotted.client.Slot;
import org.npc.slotted.client.SlottedController;

public class BasePlace extends MappedSlottedPlace {
    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }
}
