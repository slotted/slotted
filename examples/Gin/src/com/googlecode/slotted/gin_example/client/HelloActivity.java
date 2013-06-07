package com.googlecode.slotted.gin_example.client;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.slotted.client.SlottedActivity;

import javax.inject.Inject;

public class HelloActivity extends SlottedActivity implements HelloView.Presenter {
    @Inject HelloView helloView;

    public HelloActivity() {
    }

    /**
     * Invoked by the ActivityManager to start a new Activity
     */
    @Override
    public void start(AcceptsOneWidget containerWidget) {
        HelloPlace place = getCurrentPlace(HelloPlace.class);
        helloView.setName(place.getHelloName());
        helloView.setPresenter(this);
        containerWidget.setWidget(helloView.asWidget());
    }

    /**
     * Ask user before stopping this activity
     */
    @Override
    public String mayStop() {
        return "Please hold on. This activity is stopping.";
    }

    /**
     * Navigate to a new Place in the browser
     */
    public void goTo(Place place) {
        getSlottedController().goTo(place);
    }
}