package com.googlecode.slotted.testharness.client.tokenizer;

import com.google.gwt.activity.shared.Activity;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedController;
import com.googlecode.slotted.client.TokenizerParameter;

public class BasePlace extends SuperPlace {
    @TokenizerParameter
    public String baseString;
    @TokenizerParameter
    public int baseInt;
    @TokenizerParameter
    public double baseDouble;
    @TokenizerParameter
    public boolean baseBoolean;

    private BasePlace() {
    }

    public BasePlace(int value) {
        superString = "super" + value;
        baseString = "" + value;
        baseInt = value;
    }

    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[0];
    }

    @Override public Activity getActivity() {
        return null;
    }
}