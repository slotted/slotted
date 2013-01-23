package com.npc.slotted.example.client;

import com.google.gwt.activity.shared.Activity;
import org.npc.slotted.client.Slot;
import org.npc.slotted.client.SlottedPlace;

public class HelloPlace extends SlottedPlace {
    public HelloPlace(String token) {
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

    @Override public Activity getActivity() {
        return AppGinjector.instance.getHelloActivity();
    }
}