package com.googlecode.slotted.testharness.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.googlecode.slotted.client.AutoHistoryMapper;
import com.googlecode.slotted.client.HistoryMapper;
import com.googlecode.slotted.client.SlottedController;
import com.googlecode.slotted.client.SlottedEventBus;
import com.googlecode.slotted.testharness.client.flow.HomePlace;

public class TestHarness implements EntryPoint {

    public static TestHarness testHarness;
    public static SlottedController slottedController;

    public static SlottedController startTestHarness() {
        if (testHarness == null) {
            testHarness = new TestHarness();
            testHarness.onModuleLoad();
        }

        return slottedController;
    }



    public void onModuleLoad() {
        HistoryMapper historyMapper = GWT.create(AutoHistoryMapper.class);
        slottedController = new SlottedController(historyMapper, new SlottedEventBus());
        slottedController.setDefaultPlace(new HomePlace());

        SimplePanel rootDisplay = new SimplePanel();
        RootPanel.get().add(rootDisplay);
        slottedController.setDisplay(rootDisplay);
    }
}
