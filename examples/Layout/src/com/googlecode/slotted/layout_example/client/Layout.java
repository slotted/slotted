package com.googlecode.slotted.layout_example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.googlecode.slotted.client.SlottedController;

public class Layout implements EntryPoint {

    public void onModuleLoad() {
        SlottedController slottedController = AppGinjector.instance.getSlottedController();
        slottedController.setDefaultPlace(new HomePlace());
        SimpleLayoutPanel rootSlot = new SimpleLayoutPanel();
        rootSlot.setStyleName(LayoutStyle.css.rootSlot());
        RootLayoutPanel.get().add(rootSlot);
        // Goes to the place represented on URL else default place
        slottedController.setDisplay(rootSlot);
    }
}
