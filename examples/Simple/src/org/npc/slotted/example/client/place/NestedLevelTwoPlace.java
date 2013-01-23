package org.npc.slotted.example.client.place;

import com.google.gwt.activity.shared.Activity;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedPlace;
import org.npc.slotted.example.client.activity.NestedLevelTwoActivity;

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
