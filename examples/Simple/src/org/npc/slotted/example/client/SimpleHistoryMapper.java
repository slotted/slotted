package org.npc.slotted.example.client;

import org.npc.slotted.client.HistoryMapper;
import org.npc.slotted.example.client.place.HomePlace;
import org.npc.slotted.example.client.place.NestedLevelTwoPlace;
import org.npc.slotted.example.client.place.NestedPlace;
import org.npc.slotted.example.client.place.ParentPlace;

public class SimpleHistoryMapper extends HistoryMapper {
    @Override
    protected void init() {
        registerDefaultPlace(new HomePlace(), "home");
        registerPlace(new NestedLevelTwoPlace(), "level2");
        registerPlace(new NestedPlace());
        registerPlace(new ParentPlace());
    }
}
