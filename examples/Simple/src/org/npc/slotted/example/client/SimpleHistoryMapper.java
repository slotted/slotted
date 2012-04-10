package org.npc.slotted.example.client;

import org.npc.slotted.client.HistoryMapper;
import org.npc.slotted.example.client.place.HomePlace;

public class SimpleHistoryMapper extends HistoryMapper {
    /**
     * Register all the Places that can be accessed in the application.
     */
    @Override
    protected void init() {
        registerPlace(new HomePlace(), "home");
    }
}
