package com.googlecode.slotted.simple_codesplitting.client;

import com.googlecode.slotted.client.CodeSplit;
import com.googlecode.slotted.client.PlaceActivity;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedController;
import com.googlecode.slotted.client.SlottedPlace;

@CodeSplit(NestedMapper.class)
@PlaceActivity(ParentActivity.class)
public class ParentPlace extends SlottedPlace {
    public static final Slot SLOT = new Slot(new ParentPlace(), new NestedPlace());

    public ParentPlace() {
        super();
    }

    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {SLOT};
    }
}
