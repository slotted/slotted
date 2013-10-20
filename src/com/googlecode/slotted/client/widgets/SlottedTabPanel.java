package com.googlecode.slotted.client.widgets;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.googlecode.slotted.client.SlottedPlace;

public class SlottedTabPanel extends Composite {
    private SlottedTabBar tabBar = new SlottedTabBar();
    private SimplePanel panelSlot = new SimplePanel();

    public SlottedTabPanel() {
        VerticalPanel panel = new VerticalPanel();
        panel.add(tabBar);
        panel.add(panelSlot);

        panel.setCellHeight(panelSlot, "100%");
        tabBar.setWidth("100%");

        initWidget(panel);
        setStyleName("gwt-TabPanel");
        panelSlot.setStyleName("gwt-TabPanelBottom");
    }

    public void addTab(SlottedPlace place, String label) {
        tabBar.addTab(place, label);
    }

    public AcceptsOneWidget getSlotDisplay() {
        return panelSlot;
    }
}