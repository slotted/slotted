package com.googlecode.slotted.gap_codesplitting.client;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class GoodbyeActivity extends AbstractActivity {
    // Used to obtain views, eventBus, placeController
    // Alternatively, could be injected via GIN
    private ClientFactory clientFactory;
    // Name that will be appended to "Goodbye,"
    private String name;

    public GoodbyeActivity(GoodbyePlace place, ClientFactory clientFactory) {
        this.name = place.getHelloName();
        this.clientFactory = clientFactory;
    }

    /**
     * Invoked by the ActivityManager to start a new Activity
     */
    @Override
    public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
        GoodbyeView goodbyeView = clientFactory.getGoodbyeView();
        goodbyeView.setName(name);
        containerWidget.setWidget(goodbyeView.asWidget());
    }
}