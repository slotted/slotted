package com.googlecode.slotted.simple_codesplitting.client;

import com.google.gwt.place.shared.Prefix;
import com.googlecode.slotted.client.CodeSplit;
import com.googlecode.slotted.client.PlaceActivity;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedPlace;

@Prefix("level2")
@PlaceActivity(NestedLevelTwoActivity.class)
@CodeSplit(NestedMapper.class)
public class NestedLevelTwoPlace extends SlottedPlace {
    @Override public Slot getParentSlot() {
        return NestedPlace.SLOT;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }
}
