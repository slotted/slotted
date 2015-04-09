package com.googlecode.slotted.simple_example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.googlecode.slotted.client.HistoryMapper;
import com.googlecode.slotted.client.SlottedController;
import com.googlecode.slotted.client.SlottedEventBus;
import com.googlecode.slotted.simple_example.client.place.HomePlace;

public class Simple implements EntryPoint {
    /**
     * Static access to the SlottedController
     *
     * Not Recommended - instead we recommend using GIN to inject the
     * SlottedController into the each Activity.
     */
    public static SlottedController slottedController;

    public void onModuleLoad() {
        HistoryMapper historyMapper = GWT.create(SimpleHistoryMapper.class);
        slottedController = new SlottedController(historyMapper, new SlottedEventBus());
        slottedController.setDefaultPlace(new HomePlace());

        SimplePanel rootDisplay = new SimplePanel();
        RootPanel.get().add(rootDisplay);
        slottedController.setDisplay(rootDisplay);
    }
}
