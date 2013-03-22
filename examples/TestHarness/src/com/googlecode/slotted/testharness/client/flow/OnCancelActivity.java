package com.googlecode.slotted.testharness.client.flow;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.slotted.testharness.client.TestActivity;

public class OnCancelActivity extends TestActivity {

    /**
     * Invoked by the SlottedController to refresh an Activity.  This is also called when
     * an Activity is started and doesn't override start().
     */
    @Override
    public void start(AcceptsOneWidget panel) {
        startCount++;
    }
}
