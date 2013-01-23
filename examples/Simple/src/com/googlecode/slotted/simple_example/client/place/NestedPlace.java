package com.googlecode.slotted.simple_example.client.place;

import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedActivity;
import com.googlecode.slotted.client.SlottedPlace;
import com.googlecode.slotted.simple_example.client.activity.NestedActivity;

public class NestedPlace extends SlottedPlace {
    public static final Slot SLOT = new Slot(new NestedPlace(), new NestedLevelTwoPlace());

    @Override public Slot getParentSlot() {
        return ParentPlace.SLOT;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {SLOT};
    }

    @Override
    public SlottedActivity getActivity() {
        return new NestedActivity();
    }
}
