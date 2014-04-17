package com.googlecode.slotted.layout_example.client.ui;

import com.googlecode.slotted.client.MultiParentPlace;
import com.googlecode.slotted.client.Slot;

public class MpContainerPlace extends MultiParentPlace {
    public static final Slot SLOT = new Slot(new MpContainerPlace(), new MpBasePlace());

    public Slot[] getParentSlots() {
        return new Slot[] {MpParent1Place.SLOT, MpParent2Place.SLOT};
    }

    public Slot[] getChildSlots() {
        return new Slot[] {SLOT};
    }
}
