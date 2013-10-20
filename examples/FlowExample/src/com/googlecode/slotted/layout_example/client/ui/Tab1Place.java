package com.googlecode.slotted.layout_example.client.ui;

import com.google.gwt.activity.shared.Activity;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedPlace;
import com.googlecode.slotted.layout_example.client.AppGinjector;

public class Tab1Place extends SlottedPlace {
    @Override public Slot getParentSlot() {
        return TabPanelPlace.TAB_SLOT;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }

    @Override public Activity getActivity() {
        return AppGinjector.instance.getTab1Activity();
    }
}
