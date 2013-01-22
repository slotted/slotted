package com.npc.slotted.example.client;

import org.npc.slotted.client.HistoryMapper;

public class AppHistoryMapper extends HistoryMapper {
    @Override protected void init() {
        registerDefaultPlace(new HelloPlace("World!"));
        registerPlace(new GoodbyePlace(""), "gb");
    }
}
