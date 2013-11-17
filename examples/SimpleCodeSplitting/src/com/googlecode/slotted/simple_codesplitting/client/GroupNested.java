package com.googlecode.slotted.simple_codesplitting.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.googlecode.slotted.client.GroupProvider;
import com.googlecode.slotted.client.RunActivityCallback;
import com.googlecode.slotted.client.SlottedPlace;

public class GroupNested extends GroupProvider {
    @Override public void get(final SlottedPlace place, final Callback<? super Activity, ? super Throwable> callback) {
        GWT.runAsync(new RunActivityCallback(callback) {
            @Override public Activity getActivity() {
                if (place instanceof ParentPlace) {
                    return new ParentActivity();

                } else if (place instanceof NestedPlace) {
                    return new NestedActivity();

                } else if (place instanceof NestedLevelTwoPlace) {
                    return new NestedLevelTwoActivity();
                }

                return null;
            }
        });
    }
}
