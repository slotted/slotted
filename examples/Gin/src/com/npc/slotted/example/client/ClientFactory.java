package com.npc.slotted.example.client;

import com.google.web.bindery.event.shared.EventBus;
import org.npc.slotted.client.SlottedController;

public interface ClientFactory {
    EventBus getEventBus();
    SlottedController getPlaceController();
    BaseView getBaseView();
    HelloView getHelloView();
    GoodbyeView getGoodbyeView();
}