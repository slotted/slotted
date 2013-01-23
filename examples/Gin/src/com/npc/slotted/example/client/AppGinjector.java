package com.npc.slotted.example.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.slotted.client.SlottedController;

@GinModules({AppGinModule.class})
public interface AppGinjector extends Ginjector {
    public static final AppGinjector instance = GWT.create(AppGinjector.class);

    SlottedController getSlottedController();
    EventBus getEventBus();
    BaseActivity getBaseActivity();
    GoodbyeActivity getGoodbyeActivity();
    HelloActivity getHelloActivity();
}