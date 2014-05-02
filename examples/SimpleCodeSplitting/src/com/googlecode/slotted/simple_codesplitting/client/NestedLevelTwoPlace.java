package com.googlecode.slotted.simple_codesplitting.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Prefix;
import com.googlecode.slotted.client.CodeSplitActivity;
import com.googlecode.slotted.client.CodeSplitGroup;
import com.googlecode.slotted.client.CodeSplitPlace;
import com.googlecode.slotted.client.Slot;

@Prefix("level2")
public class NestedLevelTwoPlace extends CodeSplitPlace {
    @Override public Slot getParentSlot() {
        return NestedPlace.SLOT;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }

    @CodeSplitActivity(NestedLevelTwoActivity.class)
    @Override public CodeSplitGroup getCodeSplitGroup() {
        return GWT.create(NestedGroup.class);
    }
}
