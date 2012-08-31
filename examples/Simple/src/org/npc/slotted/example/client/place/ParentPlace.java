package org.npc.slotted.example.client.place;

import org.npc.slotted.client.Slot;
import org.npc.slotted.client.SlottedActivity;
import org.npc.slotted.client.SlottedController;
import org.npc.slotted.client.SlottedPlace;
import org.npc.slotted.example.client.activity.HomeActivity;
import org.npc.slotted.example.client.activity.ParentActivity;

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

    @Override
    public SlottedActivity getActivity() {
        return new ParentActivity();
    }
}
