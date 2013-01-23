package com.googlecode.slotted.gin_example.client;

import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.slotted.client.SlottedController;

public interface ClientFactory {
    EventBus getEventBus();
    SlottedController getPlaceController();
    BaseView getBaseView();
    HelloView getHelloView();
    GoodbyeView getGoodbyeView();
}