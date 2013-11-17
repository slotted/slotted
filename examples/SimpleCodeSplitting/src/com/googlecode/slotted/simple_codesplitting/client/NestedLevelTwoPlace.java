package com.googlecode.slotted.simple_codesplitting.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.AsyncProvider;
import com.google.gwt.place.shared.Prefix;
import com.googlecode.slotted.client.ProviderPlace;
import com.googlecode.slotted.client.Slot;

@Prefix("level2")
public class NestedLevelTwoPlace extends ProviderPlace {
    @Override public Slot getParentSlot() {
        return NestedPlace.SLOT;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }

    @Override public AsyncProvider<Activity, Throwable> getActivityProvider() {
        return new GroupNested();
    }
}
