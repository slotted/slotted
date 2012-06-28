package org.npc.slotted.example.client.place;

import org.npc.slotted.client.SlottedActivity;
import org.npc.slotted.client.SlottedController;
import org.npc.slotted.client.SlottedPlace;
import org.npc.slotted.example.client.activity.HomeActivity;
import org.npc.slotted.example.client.activity.NestedActivity;
import org.npc.slotted.example.client.activity.NestedLevelTwoActivity;

public class NestedLevelTwoPlace extends SlottedPlace {
    public NestedLevelTwoPlace() {
        super(NestedActivity.SLOT);
    }

    @Override
    public SlottedActivity getActivity() {
        return new NestedLevelTwoActivity();
    }
}
