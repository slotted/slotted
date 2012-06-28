package org.npc.slotted.example.client.place;

import org.npc.slotted.client.SlottedActivity;
import org.npc.slotted.client.SlottedPlace;
import org.npc.slotted.example.client.activity.HomeActivity;
import org.npc.slotted.example.client.activity.NestedActivity;
import org.npc.slotted.example.client.activity.ParentActivity;

public class NestedPlace extends SlottedPlace {
    public NestedPlace() {
        super(ParentActivity.SLOT);
    }

    @Override
    public SlottedActivity getActivity() {
        return new NestedActivity();
    }
}
