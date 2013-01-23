package com.npc.slotted.example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import org.npc.slotted.client.SlottedController;

public class HelloMVP implements EntryPoint {
    private SimplePanel appWidget = new SimplePanel();

    public void onModuleLoad() {
        SlottedController slottedController = AppGinjector.instance.getSlottedController();
        RootPanel.get().add(appWidget);
        // Goes to the place represented on URL else default place
        slottedController.setDisplay(appWidget);
    }
}
