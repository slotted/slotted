package com.googlecode.slotted.gin_codesplitting.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.googlecode.slotted.client.CodeSplitMapper;
import com.googlecode.slotted.client.GenerateGinSingletons;
import com.googlecode.slotted.client.SlottedController;

@GenerateGinSingletons(
        fullPackage = "com.googlecode.slotted.gin_codesplitting.client",
        scanPackages = {"com.googlecode.slotted.gin_codesplitting.client"})
public class HelloMVP implements EntryPoint {
    private SimplePanel appWidget = new SimplePanel();

    public void onModuleLoad() {
        SlottedController slottedController = AppGinjector.INSTANCE.getSlottedController();
        slottedController.setDefaultPlace(new HelloPlace("World!"));
        slottedController.registerCodeSplitMapper(GapMapper.class, GWT.<CodeSplitMapper>create(GapMapper.class));
        RootPanel.get().add(appWidget);
        // Goes to the place represented on URL else default place
        slottedController.setDisplay(appWidget);
    }
}
