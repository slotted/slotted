package com.googlecode.slotted.gin_codesplitting.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.AsyncProvider;
import com.googlecode.slotted.client.ProviderPlace;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedController;

public class BasePlace extends ProviderPlace {
    public static final Slot SLOT = new Slot(new BasePlace(), new HelloPlace("Base!"));

    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {SLOT};
    }

    @Override public AsyncProvider<? extends Activity, Throwable> getActivityProvider() {
        return AppGinjector.instance.getBaseActivity();
    }
}
