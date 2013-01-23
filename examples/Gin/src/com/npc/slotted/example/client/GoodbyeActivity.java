package com.npc.slotted.example.client;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.slotted.client.SlottedActivity;

import javax.inject.Inject;

public class GoodbyeActivity extends SlottedActivity {
    @Inject GoodbyeView goodbyeView;

    public GoodbyeActivity() {
    }

    /**
     * Invoked by the ActivityManager to start a new Activity
     */
    @Override
    public void start(AcceptsOneWidget containerWidget) {
        GoodbyePlace place = (GoodbyePlace) getCurrentPlace();
        goodbyeView.setName(place.getHelloName());
        containerWidget.setWidget(goodbyeView.asWidget());
    }
}