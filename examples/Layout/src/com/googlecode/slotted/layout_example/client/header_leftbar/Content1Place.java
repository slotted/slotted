package com.googlecode.slotted.layout_example.client.header_leftbar;

import com.google.gwt.activity.shared.Activity;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedPlace;

public class Content1Place extends SlottedPlace {
    @Override public Slot getParentSlot() {
        return LeftbarPlace.LeftbarSlot;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {};
    }

    @Override public Activity getActivity() {
        return new Content1Activity();
    }
}
