package com.npc.slotted.example.client;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import org.npc.slotted.client.Slot;
import org.npc.slotted.client.SlottedActivity;

import javax.inject.Inject;

public class BaseActivity extends SlottedActivity {
    @Inject private BaseView baseView;

    public BaseActivity() {
    }

    /**
     * Invoked by the SlottedController to start a new Activity
     */
    @Override public void start(AcceptsOneWidget containerWidget) {
        containerWidget.setWidget(baseView.asWidget());
    }

    @Override public void setChildSlotDisplay(Slot slot) {
        slot.setDisplay(baseView.getSlotDisplay());
    }
}