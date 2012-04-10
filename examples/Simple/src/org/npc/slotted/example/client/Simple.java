package org.npc.slotted.example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.*;
import com.google.web.bindery.event.shared.SimpleEventBus;
import org.npc.slotted.client.SlottedController;
import org.npc.slotted.example.client.place.HomePlace;

public class Simple implements EntryPoint {
    public void onModuleLoad() {
        SlottedController controller = new SlottedController(
                new HomePlace(),
                new SimpleHistoryMapper(),
                new SimpleEventBus()
        );

        SimplePanel rootDisplay = new SimplePanel();
        RootPanel.get().add(rootDisplay);
        controller.setDisplay(rootDisplay);
    }
}
