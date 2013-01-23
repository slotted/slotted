package com.googlecode.slotted.gap_example.client;

import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.slotted.client.SlottedController;

public class HelloMVP implements EntryPoint {
    private Place defaultPlace = new HelloPlace("World!");
    private SimplePanel appWidget = new SimplePanel();

    public void onModuleLoad() {
        ClientFactory clientFactory = GWT.create(ClientFactory.class);
        EventBus eventBus = clientFactory.getEventBus();
        SlottedController slottedController = clientFactory.getPlaceController();

        // Start ActivityManager for the main widget with our ActivityMapper
        ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
        AppPlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);
        slottedController.setLegacyMappers(activityMapper, historyMapper, defaultPlace);

        RootPanel.get().add(appWidget);
        // Goes to the place represented on URL else default place
        slottedController.setDisplay(appWidget);
    }
}
