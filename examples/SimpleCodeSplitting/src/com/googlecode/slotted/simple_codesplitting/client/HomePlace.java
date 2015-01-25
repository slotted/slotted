package com.googlecode.slotted.simple_codesplitting.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.place.shared.Prefix;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedController;
import com.googlecode.slotted.client.SlottedPlace;

@Prefix("home")
public class HomePlace extends SlottedPlace {
    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }

    @Override public void getActivity(final Callback<? super Activity, ? super Throwable> callback) {
        GWT.runAsync(new RunAsyncCallback() {
            @Override public void onFailure(Throwable reason) {
                callback.onFailure(reason);
            }

            @Override public void onSuccess() {
                callback.onSuccess(new HomeActivity());
            }
        });
    }
}
