package com.googlecode.slotted.layout_example.client.header_leftbar;

import com.google.gwt.activity.shared.Activity;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedController;
import com.googlecode.slotted.client.SlottedPlace;

public class HeaderPlace extends SlottedPlace {
    public static final Slot HeaderSlot = new Slot(new HeaderPlace(), new LeftbarPlace());

    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {HeaderSlot};
    }

    @Override public Activity getActivity() {
        return new HeaderActivity();
    }
}
