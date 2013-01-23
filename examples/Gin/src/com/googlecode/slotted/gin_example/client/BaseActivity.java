package com.googlecode.slotted.gin_example.client;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedActivity;

import javax.inject.Inject;

public class BaseActivity extends SlottedActivity {
    @Inject private BaseView baseView;

    public BaseActivity() {
    }

    /**
     * Invoked by the SlottedController to start a new Activity
     */
    @Override public void start(AcceptsOneWidget containerWidget) {
        containerWidget.setWidget(baseView.asWidget());
    }

    @Override public void setChildSlotDisplay(Slot slot) {
        slot.setDisplay(baseView.getSlotDisplay());
    }
}