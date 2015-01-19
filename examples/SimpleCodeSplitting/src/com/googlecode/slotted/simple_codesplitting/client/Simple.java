package com.googlecode.slotted.simple_codesplitting.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.googlecode.slotted.client.AutoHistoryMapper;
import com.googlecode.slotted.client.CodeSplitMapper;
import com.googlecode.slotted.client.HistoryMapper;
import com.googlecode.slotted.client.SlottedController;
import com.googlecode.slotted.client.SlottedEventBus;

public class Simple implements EntryPoint {
    /**
     * Static access to the SlottedController
     *
     * Not Recommended - instead we recommend using GIN to inject the
     * SlottedController into the each Activity.
     */
    public static SlottedController slottedController;

    public void onModuleLoad() {
        HistoryMapper historyMapper = GWT.create(AutoHistoryMapper.class);
        slottedController = new SlottedController(historyMapper, new SlottedEventBus());
        slottedController.setDefaultPlace(new HomePlace());
        slottedController.registerCodeSplitMapper(NestedMapper.class, GWT.<CodeSplitMapper>create(NestedMapper.class));

        SimplePanel rootDisplay = new SimplePanel();
        RootPanel.get().add(rootDisplay);
        slottedController.setDisplay(rootDisplay);
    }
}
