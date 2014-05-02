package com.googlecode.slotted.gin_codesplitting.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.googlecode.slotted.client.CodeSplitGroup;
import com.googlecode.slotted.client.SlottedException;
import com.googlecode.slotted.client.SlottedPlace;

public class GapSplitGroup implements CodeSplitGroup {
    private static GapGinjector gapGinjector;

    @Override public void get(final SlottedPlace place, final Callback<? super Activity, ? super Throwable> callback) {
        GWT.runAsync(new RunAsyncCallback() {
            @Override public void onFailure(Throwable reason) {
                callback.onFailure(reason);
            }

            @Override public void onSuccess() {
                GapGinjector gapGinjector = GWT.create(GapGinjector.class);

                if (place instanceof HelloPlace) {
                    callback.onSuccess(gapGinjector.getHelloActivity());

                } else if (place instanceof GoodbyePlace) {
                    callback.onSuccess(gapGinjector.getGoodbyeActivity());

                } else {
                    callback.onFailure(new SlottedException(place.getClass().getName() + " is not found."));
                }
            }
        });
    }
}
