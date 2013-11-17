package com.googlecode.slotted.simple_codesplitting.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.AsyncProvider;
import com.googlecode.slotted.client.ProviderPlace;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedController;

public class ParentPlace extends ProviderPlace {
    public static final Slot SLOT = new Slot(new ParentPlace(), new NestedPlace());

    public ParentPlace() {
        super();
    }

    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {SLOT};
    }

    @Override public AsyncProvider<Activity, Throwable> getActivityProvider() {
        return new GroupNested();
    }
}
