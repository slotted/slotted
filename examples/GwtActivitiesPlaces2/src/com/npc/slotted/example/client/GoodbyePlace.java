package com.npc.slotted.example.client;

import org.npc.slotted.client.MappedSlottedPlace;
import org.npc.slotted.client.Slot;

public class GoodbyePlace extends MappedSlottedPlace {

    public GoodbyePlace(String token) {
        setParameter("helloName", token);
    }

    public String getHelloName() {
        return getParameter("helloName");
    }

    @Override public Slot getParentSlot() {
        return BasePlace.SLOT;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }
}