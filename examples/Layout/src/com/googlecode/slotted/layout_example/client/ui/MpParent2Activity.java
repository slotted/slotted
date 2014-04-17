package com.googlecode.slotted.layout_example.client.ui;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedActivity;

public class MpParent2Activity extends SlottedActivity {
    private SimplePanel slotPanel;

    @Override public void start(AcceptsOneWidget containerWidget) {
        containerWidget.setWidget(createUi());
    }

    private IsWidget createUi() {
        VerticalPanel mainPanel = new VerticalPanel();
        mainPanel.add(new Label("Parent2"));

        slotPanel = new SimplePanel();
        mainPanel.add(slotPanel);

        return mainPanel;
    }

    @Override public AcceptsOneWidget getChildSlotDisplay(Slot slot) {
        if (slot == MpParent2Place.SLOT) {
            return slotPanel;
        }
        return null;
    }
}