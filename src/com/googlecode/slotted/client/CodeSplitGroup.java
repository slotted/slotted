package com.googlecode.slotted.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.Callback;

public interface CodeSplitGroup {
    abstract public void get(SlottedPlace place, Callback<? super Activity, ? super Throwable> callback);
}
