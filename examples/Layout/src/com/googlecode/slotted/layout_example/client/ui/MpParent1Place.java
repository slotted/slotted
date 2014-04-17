package com.googlecode.slotted.layout_example.client.ui;

import com.google.gwt.activity.shared.Activity;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedPlace;
import com.googlecode.slotted.layout_example.client.AppGinjector;

public class MpParent1Place extends SlottedPlace{
    public static final Slot SLOT = new Slot(new MpParent1Place(), new MpContainerPlace());

    public Slot getParentSlot() {
        return MainPlace.SLOT;
    }

    public Slot[] getChildSlots() {
        return new Slot[] {SLOT};
    }

    public Activity getActivity() {
        return AppGinjector.instance.getMpParent1Activity();
    }
}
