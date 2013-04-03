package com.googlecode.slotted.simple_example.client.place;

import com.google.gwt.place.shared.Prefix;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedActivity;
import com.googlecode.slotted.client.SlottedController;
import com.googlecode.slotted.client.SlottedPlace;
import com.googlecode.slotted.simple_example.client.activity.HomeActivity;

@Prefix("home")
public class HomePlace extends SlottedPlace {
    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }

    @Override
    public SlottedActivity getActivity() {
        return new HomeActivity();
    }
}
