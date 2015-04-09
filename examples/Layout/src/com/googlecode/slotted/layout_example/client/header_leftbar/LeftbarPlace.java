package com.googlecode.slotted.layout_example.client.header_leftbar;

import com.google.gwt.activity.shared.Activity;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedPlace;

public class LeftbarPlace extends SlottedPlace {
    public static final Slot LeftbarSlot = new Slot(new LeftbarPlace(), new Content1Place());

    @Override public Slot getParentSlot() {
        return HeaderPlace.HeaderSlot;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {LeftbarSlot};
    }

    @Override public Activity getActivity() {
        return new LeftbarActivity();
    }
}
