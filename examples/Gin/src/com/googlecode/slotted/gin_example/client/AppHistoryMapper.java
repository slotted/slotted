package com.googlecode.slotted.gin_example.client;

import com.googlecode.slotted.client.HistoryMapper;

public class AppHistoryMapper extends HistoryMapper {
    @Override protected void init() {
        registerDefaultPlace(new HelloPlace("World!"));
        registerPlace(new GoodbyePlace(""), "gb");
        registerPlace(new BasePlace());
    }
}