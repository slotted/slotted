package com.googlecode.slotted.gin_example.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Prefix;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedPlace;
import com.googlecode.slotted.client.TokenizerParameter;

@Prefix("hp")
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

    @Override public Activity getActivity() {
        return AppGinjector.instance.getHelloActivity();
    }
}