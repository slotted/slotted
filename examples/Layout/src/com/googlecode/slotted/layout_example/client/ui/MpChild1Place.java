package com.googlecode.slotted.layout_example.client.ui;

import com.google.gwt.activity.shared.Activity;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedPlace;
import com.googlecode.slotted.layout_example.client.AppGinjector;

public class MpChild1Place extends SlottedPlace{
    public Slot getParentSlot() {
        return MpBasePlace.SLOT;
    }

    public Slot[] getChildSlots() {
        return null;
    }

    public Activity getActivity() {
        return AppGinjector.instance.getMpChild1Activity();
    }
}
