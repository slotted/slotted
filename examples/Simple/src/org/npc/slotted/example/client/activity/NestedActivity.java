package org.npc.slotted.example.client.activity;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.web.bindery.event.shared.EventBus;
import org.npc.slotted.client.AbstractSlottedActivity;
import org.npc.slotted.client.ActiveSlot;
import org.npc.slotted.client.PlaceParameters;
import org.npc.slotted.client.Slot;
import org.npc.slotted.example.client.place.NestedLevelTwoPlace;
import org.npc.slotted.example.client.place.NestedPlace;
import org.npc.slotted.example.client.place.ParentPlace;

public class NestedActivity extends AbstractSlottedActivity {
    private SimplePanel slotPNL;

    /**
     * Invoked by the SlottedController to refresh an Activity.  This is also called when
     * an Activity is started and doesn't override start().
     */
    @Override
    public void refresh(AcceptsOneWidget panel, PlaceParameters parameters, EventBus eventBus) {
        VerticalPanel mainPNL = new VerticalPanel();
        panel.setWidget(mainPNL);

        mainPNL.add(new HTML("Nested View"));

        slotPNL = new SimplePanel();
        mainPNL.add(slotPNL);
    }

    @Override public void setChildSlotDisplay(Slot slot) {
        slot.setDisplay(slotPNL);
    }

}
