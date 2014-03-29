package com.googlecode.slotted.activity_cache.client;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedActivity;

public class CacheBaseActivity extends SlottedActivity {
    private AcceptsOneWidget panel;

    @Override
    public void start(AcceptsOneWidget panel) {
        this.panel = panel;
    }

    @Override public AcceptsOneWidget getChildSlotDisplay(Slot slot) {
        return panel;
    }
}
