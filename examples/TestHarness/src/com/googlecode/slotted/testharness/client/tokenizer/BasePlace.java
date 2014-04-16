package com.googlecode.slotted.testharness.client.tokenizer;

import com.google.gwt.activity.shared.Activity;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedController;
import com.googlecode.slotted.client.TokenizerParameter;

import java.sql.Timestamp;
import java.util.Date;

public class BasePlace extends SuperPlace {
    @TokenizerParameter
    public String baseString;
    @TokenizerParameter
    public String baseEmptyString;
    @TokenizerParameter
    public int baseInt;
    @TokenizerParameter
    public double baseDouble;
    @TokenizerParameter
    public boolean baseBoolean;
    @TokenizerParameter
    public Date baseDate;
    @TokenizerParameter
    public Timestamp baseTimestamp;

    private BasePlace() {
    }

    public BasePlace(int value, Date date, Timestamp timestamp) {
        superString = "super" + value;
        baseString = "" + value;
        baseInt = value;
        baseDate = date;
        baseTimestamp = timestamp;
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