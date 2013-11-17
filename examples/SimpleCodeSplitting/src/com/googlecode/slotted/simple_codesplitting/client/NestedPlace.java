package com.googlecode.slotted.simple_codesplitting.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.AsyncProvider;
import com.googlecode.slotted.client.ProviderPlace;
import com.googlecode.slotted.client.Slot;

public class NestedPlace extends ProviderPlace {
    public static final Slot SLOT = new Slot(new NestedPlace(), new NestedLevelTwoPlace());

    @Override public Slot getParentSlot() {
        return ParentPlace.SLOT;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {SLOT};
    }

    @Override public AsyncProvider<Activity, Throwable> getActivityProvider() {
        return new GroupNested();
    }
}
