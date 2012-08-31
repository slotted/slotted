package org.npc.slotted.example.client.place;

import org.npc.slotted.client.Slot;
import org.npc.slotted.client.SlottedActivity;
import org.npc.slotted.client.SlottedPlace;
import org.npc.slotted.example.client.activity.HomeActivity;
import org.npc.slotted.example.client.activity.NestedActivity;
import org.npc.slotted.example.client.activity.ParentActivity;

public class NestedPlace extends SlottedPlace {
    public static final Slot SLOT = new Slot(new NestedPlace(), new NestedLevelTwoPlace());

    @Override public Slot getParentSlot() {
        return ParentPlace.SLOT;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[0];
    }

    @Override
    public SlottedActivity getActivity() {
        return new NestedActivity();
    }
}
