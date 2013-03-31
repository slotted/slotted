package com.googlecode.slotted.gap2_example.client;

import com.google.gwt.place.shared.Prefix;
import com.googlecode.slotted.client.MappedSlottedPlace;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedController;

@Prefix("b")
public class BasePlace extends MappedSlottedPlace {
    public static final Slot SLOT = new Slot(new BasePlace(), new HelloPlace("Base!"));

    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {SLOT};
    }
}
