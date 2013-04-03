package com.googlecode.slotted.simple_example.client.place;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Prefix;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedPlace;
import com.googlecode.slotted.simple_example.client.activity.NestedLevelTwoActivity;

@Prefix("level2")
public class NestedLevelTwoPlace extends SlottedPlace {
    @Override public Slot getParentSlot() {
        return NestedPlace.SLOT;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }

    @Override public Activity getActivity() {
        return new NestedLevelTwoActivity();
    }
}
