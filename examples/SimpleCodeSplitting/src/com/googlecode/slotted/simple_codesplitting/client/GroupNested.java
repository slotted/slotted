package com.googlecode.slotted.simple_codesplitting.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.googlecode.slotted.client.CodeSplitGroup;
import com.googlecode.slotted.client.SlottedException;
import com.googlecode.slotted.client.SlottedPlace;

public class GroupNested extends CodeSplitGroup {
    @Override public void get(final SlottedPlace place, final Callback<? super Activity, ? super Throwable> callback) {
        GWT.runAsync(new RunAsyncCallback() {
            @Override public void onFailure(Throwable reason) {
                callback.onFailure(reason);
            }

            @Override public void onSuccess() {
                if (place instanceof ParentPlace) {
                    callback.onSuccess(new ParentActivity());

                } else if (place instanceof NestedPlace) {
                    callback.onSuccess(new NestedActivity());

                } else if (place instanceof NestedLevelTwoPlace) {
                    callback.onSuccess(new NestedLevelTwoActivity());

                } else {
                    callback.onFailure(new SlottedException(place.getClass().getName() + " is not found."));
                }
            }
        });
    }
}
