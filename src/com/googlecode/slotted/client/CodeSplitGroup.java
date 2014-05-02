package com.googlecode.slotted.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.Callback;

abstract public class CodeSplitGroup {
    abstract public void get(SlottedPlace place, Callback<? super Activity, ? super Throwable> callback);
}
