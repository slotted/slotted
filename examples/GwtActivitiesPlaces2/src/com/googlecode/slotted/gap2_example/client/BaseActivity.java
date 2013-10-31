package com.googlecode.slotted.gap2_example.client;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedActivity;

public class BaseActivity extends SlottedActivity {
    // Used to obtain views
    private ClientFactory clientFactory;
    private BaseView baseView;

    public BaseActivity(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    /**
     * Invoked by the SlottedController to start a new Activity
     */
    @Override public void start(AcceptsOneWidget containerWidget) {
        baseView = clientFactory.getBaseView();
        containerWidget.setWidget(baseView.asWidget());
    }

    @Override public AcceptsOneWidget getChildSlotDisplay(Slot slot) {
        if (BasePlace.SLOT == slot) {
            return baseView.getSlotDisplay();
        }
        return null;
    }
}