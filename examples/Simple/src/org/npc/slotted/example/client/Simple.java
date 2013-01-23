package org.npc.slotted.example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.googlecode.slotted.client.SlottedController;

public class Simple implements EntryPoint {
    /**
     * Static access to the SlottedController
     *
     * Not Recommended - instead we recommend using GIN to inject the
     * SlottedController into the each Activity.
     */
    public static SlottedController slottedController;

    public void onModuleLoad() {
        slottedController = new SlottedController(
                new SimpleHistoryMapper(),
                new SimpleEventBus()
        );

        SimplePanel rootDisplay = new SimplePanel();
        RootPanel.get().add(rootDisplay);
        slottedController.setDisplay(rootDisplay);
    }
}
