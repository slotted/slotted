package org.npc.slotted.example.client.place;

import org.npc.slotted.client.SlottedActivity;
import org.npc.slotted.client.SlottedController;
import org.npc.slotted.client.SlottedPlace;
import org.npc.slotted.example.client.activity.HomeActivity;
import org.npc.slotted.example.client.activity.ParentActivity;

public class ParentPlace extends SlottedPlace {
    public ParentPlace() {
        super(SlottedController.RootSlot);
    }

    @Override
    public SlottedActivity getActivity() {
        return new ParentActivity();
    }
}
