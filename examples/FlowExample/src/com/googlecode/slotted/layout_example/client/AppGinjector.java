package com.googlecode.slotted.layout_example.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.googlecode.slotted.client.SlottedController;
import com.googlecode.slotted.layout_example.client.ui.MainActivity;
import com.googlecode.slotted.layout_example.client.ui.Tab1Activity;
import com.googlecode.slotted.layout_example.client.ui.Tab2Activity;
import com.googlecode.slotted.layout_example.client.ui.TabPanelActivity;

@GinModules({AppGinModule.class})
public interface AppGinjector extends Ginjector {
    public static final AppGinjector instance = GWT.create(AppGinjector.class);

    SlottedController getSlottedController();
    MainActivity getMainActivity();
    Tab1Activity getTab1Activity();
    Tab2Activity getTab2Activity();
    TabPanelActivity getTabPanelActivity();
}