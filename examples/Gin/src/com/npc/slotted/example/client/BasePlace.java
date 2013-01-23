package com.npc.slotted.example.client;

import com.google.gwt.activity.shared.Activity;
import org.npc.slotted.client.Slot;
import org.npc.slotted.client.SlottedController;
import org.npc.slotted.client.SlottedPlace;

public class BasePlace extends SlottedPlace {
    public static final Slot SLOT = new Slot(new BasePlace(), new HelloPlace("Base!"));

    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {SLOT};
    }

    @Override public Activity getActivity() {
        return AppGinjector.instance.getBaseActivity();
    }
}
