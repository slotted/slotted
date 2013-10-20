package com.googlecode.slotted.layout_example.client.ui;

import com.google.gwt.activity.shared.Activity;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedPlace;
import com.googlecode.slotted.layout_example.client.AppGinjector;

public class TabPanelPlace extends SlottedPlace {
    public static final Slot TAB_SLOT = new Slot(new TabPanelPlace(), new Tab1Place());

    @Override public Slot getParentSlot() {
        return MainPlace.SLOT;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {TAB_SLOT};
    }

    @Override public Activity getActivity() {
        return AppGinjector.instance.getTabPanelActivity();
    }
}
