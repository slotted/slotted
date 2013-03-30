package com.googlecode.slotted.simple_example.client;

import com.googlecode.slotted.client.HistoryMapper;
import com.googlecode.slotted.simple_example.client.place.HomePlace;
import com.googlecode.slotted.simple_example.client.place.NestedLevelTwoPlace;
import com.googlecode.slotted.simple_example.client.place.NestedPlace;
import com.googlecode.slotted.simple_example.client.place.ParentPlace;

public class SimpleHistoryMapper extends HistoryMapper {
    @Override
    protected void init() {
        setDefaultPlace(new HomePlace());
        registerPlace(HomePlace.class, "home");
        registerPlace(NestedLevelTwoPlace.class, "level2");
        registerPlace(NestedPlace.class);
        registerPlace(ParentPlace.class);
    }
}
