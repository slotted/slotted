package com.googlecode.slotted.gin_codesplitting.client;

import com.google.gwt.place.shared.Prefix;
import com.googlecode.slotted.client.CodeSplitMapperClass;
import com.googlecode.slotted.client.PlaceActivity;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedPlace;
import com.googlecode.slotted.client.TokenizerParameter;

@Prefix("hp")
@CodeSplitMapperClass(GapMapper.class)
@PlaceActivity(HelloActivity.class)
public class HelloPlace extends SlottedPlace {
    @TokenizerParameter
    private String helloName;

    private HelloPlace() {}

    public HelloPlace(String token) {
        this.helloName = token;
    }

    public String getHelloName() {
        return helloName;
    }

    @Override public Slot getParentSlot() {
        return BasePlace.SLOT;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }
}