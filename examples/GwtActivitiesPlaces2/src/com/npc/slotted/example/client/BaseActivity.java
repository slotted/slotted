package com.npc.slotted.example.client;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import org.npc.slotted.client.SlottedActivity;

public class BaseActivity extends SlottedActivity {
    // Used to obtain views
    private ClientFactory clientFactory;

    public BaseActivity(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    /**
     * Invoked by the SlottedController to start a new Activity
     */
    @Override public void start(AcceptsOneWidget containerWidget) {
        BaseView baseView = clientFactory.getBaseView();
        containerWidget.setWidget(baseView.asWidget());
    }
}