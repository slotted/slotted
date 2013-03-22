package com.googlecode.slotted.testharness.client.flow;

import com.google.gwt.activity.shared.Activity;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedController;
import com.googlecode.slotted.client.SlottedPlace;

public class GoToPlace extends SlottedPlace {
    public static final Slot Slot1 = new Slot(new GoToPlace(), new GoTo1aPlace());
    public static final Slot Slot2 = new Slot(new GoToPlace(), new GoTo2aPlace());

    public static GoToActivity activity = new GoToActivity();

    public GoToPlace() {
        super();
    }

    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {Slot1, Slot2};
    }

    @Override public Activity getActivity() {
        return activity;
    }
}
