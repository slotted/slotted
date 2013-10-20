package com.googlecode.slotted.layout_example.client.ui;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedActivity;
import com.googlecode.slotted.client.widgets.SlottedTabPanel;

public class TabPanelActivity extends SlottedActivity {

    private SlottedTabPanel tabPanel;

    @Override public void start(AcceptsOneWidget containerWidget) {
        containerWidget.setWidget(createUi());
    }

    private IsWidget createUi() {
        tabPanel = new SlottedTabPanel();

        tabPanel.addTab(new Tab1Place(), "Tab1");
        tabPanel.addTab(new Tab2Place(), "Tab2");

        return tabPanel;
    }


    @Override public AcceptsOneWidget getChildSlotDisplay(Slot slot) {
        if (slot == TabPanelPlace.TAB_SLOT) {
            return tabPanel.getSlotDisplay();
        }
        return null;
    }
}