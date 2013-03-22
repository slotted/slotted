package com.googlecode.slotted.testharness.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.googlecode.slotted.client.SlottedController;

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
        slottedController = new SlottedController(
                new TestHarnessHistoryMapper(),
                new SimpleEventBus()
        );

        SimplePanel rootDisplay = new SimplePanel();
        RootPanel.get().add(rootDisplay);
        slottedController.setDisplay(rootDisplay);
    }
}
