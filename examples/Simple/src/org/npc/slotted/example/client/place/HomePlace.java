package org.npc.slotted.example.client.place;

import org.npc.slotted.client.SlottedActivity;
import org.npc.slotted.client.SlottedController;
import org.npc.slotted.client.SlottedPlace;
import org.npc.slotted.example.client.activity.HomeActivity;

public class HomePlace extends SlottedPlace {
    public HomePlace() {
        super(SlottedController.RootSlot);
    }

    @Override
    public SlottedActivity getActivity() {
        return new HomeActivity();
    }
}
