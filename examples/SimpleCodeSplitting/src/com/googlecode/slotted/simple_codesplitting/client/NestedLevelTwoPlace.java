package com.googlecode.slotted.simple_codesplitting.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Prefix;
import com.googlecode.slotted.client.CodeSplitMapper;
import com.googlecode.slotted.client.CodeSplitMapperClass;
import com.googlecode.slotted.client.CodeSplitPlace;
import com.googlecode.slotted.client.PlaceActivity;
import com.googlecode.slotted.client.Slot;

@Prefix("level2")
@PlaceActivity(NestedLevelTwoActivity.class)
@CodeSplitMapperClass(NestedMapper.class)
public class NestedLevelTwoPlace extends CodeSplitPlace {
    @Override public Slot getParentSlot() {
        return NestedPlace.SLOT;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }

    @Override public CodeSplitMapper getCodeSplitGroup() {
        return GWT.create(NestedMapper.class);
    }
}
