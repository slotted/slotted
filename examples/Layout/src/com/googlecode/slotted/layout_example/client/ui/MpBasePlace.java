package com.googlecode.slotted.layout_example.client.ui;

import com.google.gwt.activity.shared.Activity;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedPlace;
import com.googlecode.slotted.layout_example.client.AppGinjector;

public class MpBasePlace extends SlottedPlace{
    public static final Slot SLOT = new Slot(new MpBasePlace(), new MpChild1Place());

    public Slot getParentSlot() {
        return MpContainerPlace.SLOT;
    }

    public Slot[] getChildSlots() {
        return new Slot[] {SLOT};
    }

    public Activity getActivity() {
        return AppGinjector.instance.getMpBaseActivity();
    }
}
