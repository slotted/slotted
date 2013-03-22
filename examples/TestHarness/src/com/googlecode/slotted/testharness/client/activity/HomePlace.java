package com.googlecode.slotted.testharness.client.activity;

import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedActivity;
import com.googlecode.slotted.client.SlottedController;
import com.googlecode.slotted.client.SlottedPlace;

public class HomePlace extends SlottedPlace {
    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }

    @Override
    public SlottedActivity getActivity() {
        return new HomeActivity();
    }
}
