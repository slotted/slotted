package com.googlecode.slotted.gap2_example.client;

import com.googlecode.slotted.client.MappedSlottedPlace;
import com.googlecode.slotted.client.Slot;

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