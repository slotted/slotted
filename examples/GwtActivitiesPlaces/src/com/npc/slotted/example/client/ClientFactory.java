package com.npc.slotted.example.client;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;

public interface ClientFactory {
    EventBus getEventBus();
    PlaceController getPlaceController();
    HelloView getHelloView();
    GoodbyeView getGoodbyeView();
}