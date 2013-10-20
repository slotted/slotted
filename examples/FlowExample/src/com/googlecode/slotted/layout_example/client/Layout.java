package com.googlecode.slotted.layout_example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.googlecode.slotted.client.SlottedController;
import com.googlecode.slotted.layout_example.client.ui.MainPlace;

public class Layout implements EntryPoint {

    public void onModuleLoad() {
        SlottedController slottedController = AppGinjector.instance.getSlottedController();
        slottedController.setDefaultPlace(new MainPlace());
        SimpleLayoutPanel appWidget = new SimpleLayoutPanel();
        RootLayoutPanel.get().add(appWidget);
        // Goes to the place represented on URL else default place
        slottedController.setDisplay(appWidget);
    }
}
