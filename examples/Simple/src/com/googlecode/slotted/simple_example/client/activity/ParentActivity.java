package com.googlecode.slotted.simple_example.client.activity;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedActivity;

public class ParentActivity extends SlottedActivity {
    private SimplePanel slotPNL;

    @Override
    public void start(AcceptsOneWidget panel) {
        VerticalPanel mainPNL = new VerticalPanel();
        panel.setWidget(mainPNL);

        mainPNL.add(new HTML("Parent View"));

        slotPNL = new SimplePanel();
        mainPNL.add(slotPNL);
    }

    @Override public void setChildSlotDisplay(Slot slot) {
        slot.setDisplay(slotPNL);
    }
}
