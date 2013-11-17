package com.googlecode.slotted.simple_codesplitting.client;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedActivity;

public class NestedActivity extends SlottedActivity {
    private SimplePanel slotPNL;

    @Override public void start(AcceptsOneWidget panel) {
        VerticalPanel mainPNL = new VerticalPanel();
        panel.setWidget(mainPNL);

        mainPNL.add(new HTML("Nested View"));

        slotPNL = new SimplePanel();
        mainPNL.add(slotPNL);
    }

    @Override public AcceptsOneWidget getChildSlotDisplay(Slot slot) {
        if (NestedPlace.SLOT == slot) {
            return slotPNL;
        }
        return null;
    }
}
