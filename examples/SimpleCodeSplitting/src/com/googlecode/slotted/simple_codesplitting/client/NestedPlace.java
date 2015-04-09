package com.googlecode.slotted.simple_codesplitting.client;

import com.googlecode.slotted.client.CodeSplit;
import com.googlecode.slotted.client.PlaceActivity;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedPlace;

@CodeSplit(NestedMapper.class)
@PlaceActivity(NestedActivity.class)
public class NestedPlace extends SlottedPlace {
    public static final Slot SLOT = new Slot(new NestedPlace(), new NestedLevelTwoPlace());

    @Override public Slot getParentSlot() {
        return ParentPlace.SLOT;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {SLOT};
    }
}
