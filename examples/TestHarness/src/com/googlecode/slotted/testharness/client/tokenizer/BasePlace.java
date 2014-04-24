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
    public byte baseByte;
    @TokenizerParameter
    public short baseShort;
    @TokenizerParameter
    public int baseInt;
    @TokenizerParameter
    public float baseFloat;
    @TokenizerParameter
    public double baseDouble;
    @TokenizerParameter
    public boolean baseBoolean;
    @TokenizerParameter
    public char baseChar;
    @TokenizerParameter
    public String baseEmptyString;
    @TokenizerParameter
    public Byte baseByteObj;
    @TokenizerParameter
    public Short baseShortObj;
    @TokenizerParameter
    public Integer baseIntegerObj;
    @TokenizerParameter
    public Long baseLongObj;
    @TokenizerParameter
    public Float baseFloatObj;
    @TokenizerParameter
    public Double baseDoubleObj;
    @TokenizerParameter
    public Boolean baseBooleanObj;
    @TokenizerParameter
    public Character baseCharacterObj;
    @TokenizerParameter
    public Date baseDate;
    @TokenizerParameter
    public Timestamp baseTimestamp;

    public BasePlace() {
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