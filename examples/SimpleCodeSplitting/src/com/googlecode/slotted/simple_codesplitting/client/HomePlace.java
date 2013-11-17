package com.googlecode.slotted.simple_codesplitting.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Prefix;
import com.googlecode.slotted.client.AsyncPlace;
import com.googlecode.slotted.client.RunActivityCallback;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedController;

@Prefix("home")
public class HomePlace extends AsyncPlace {
    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }

    @Override public void getAsyncActivity(final Callback<Activity, Throwable> callback) {
        GWT.runAsync(new RunActivityCallback(callback) {
            @Override public Activity getActivity() {
                return new HomeActivity();
            }
        });
    }
}
