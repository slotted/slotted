package com.googlecode.slotted.testharness.client.flow;

import com.google.gwt.activity.shared.Activity;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedController;
import com.googlecode.slotted.client.SlottedPlace;

public class OnCancelPlace extends SlottedPlace {
    public static OnCancelActivity activity = new OnCancelActivity();

    public OnCancelPlace() {
        super();
    }

    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }

    @Override public Activity getActivity() {
        return activity;
    }
}
