package com.npc.slotted.example.client;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import org.npc.slotted.client.SlottedController;

public interface ClientFactory {
    EventBus getEventBus();
    SlottedController getPlaceController();
    HelloView getHelloView();
    GoodbyeView getGoodbyeView();
}