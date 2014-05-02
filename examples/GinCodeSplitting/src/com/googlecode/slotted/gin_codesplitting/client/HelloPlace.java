package com.googlecode.slotted.gin_codesplitting.client;

import com.google.gwt.place.shared.Prefix;
import com.googlecode.slotted.client.CodeSplitGroup;
import com.googlecode.slotted.client.CodeSplitPlace;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.TokenizerParameter;

@Prefix("hp")
public class HelloPlace extends CodeSplitPlace {
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

    @Override public CodeSplitGroup getCodeSplitGroup() {
        return new GapSplitGroup();
    }
}