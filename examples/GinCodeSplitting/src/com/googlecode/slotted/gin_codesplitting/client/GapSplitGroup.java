package com.googlecode.slotted.gin_codesplitting.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.googlecode.slotted.client.GroupProvider;
import com.googlecode.slotted.client.RunActivityCallback;
import com.googlecode.slotted.client.SlottedPlace;

public class GapSplitGroup extends GroupProvider {
    private static GapGinjector gapGinjector;

    @Override public void get(final SlottedPlace place, final Callback<? super Activity, ? super Throwable> callback) {
        GWT.runAsync(new RunActivityCallback(callback) {
            @Override public Activity getActivity() {
                GapGinjector gapGinjector = GWT.create(GapGinjector.class);

                if (place instanceof HelloPlace) {
                    return gapGinjector.getHelloActivity();

                } else if (place instanceof GoodbyePlace) {
                    return gapGinjector.getGoodbyeActivity();

                }

                return null;
            }
        });
    }
}
