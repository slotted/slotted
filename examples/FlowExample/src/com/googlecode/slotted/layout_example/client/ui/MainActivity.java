package com.googlecode.slotted.layout_example.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedActivity;
import com.googlecode.slotted.client.widgets.SlottedHyperlink;

public class MainActivity extends SlottedActivity {
    interface MainActivityUiBinder extends UiBinder<Widget, MainActivity> {}
    private static MainActivityUiBinder uiBinder = GWT.create(MainActivityUiBinder.class);
    @UiField VerticalPanel menuPNL;
    @UiField SimplePanel slotPNL;

    @Override public void start(AcceptsOneWidget containerWidget) {
        containerWidget.setWidget(uiBinder.createAndBindUi(this));
        createMenu();
    }

    private void createMenu() {
        menuPNL.add(new SlottedHyperlink("TabPanel", new TabPanelPlace()));
    }

    @Override public AcceptsOneWidget getChildSlotDisplay(Slot slot) {
        if (slot == MainPlace.SLOT) {
            return slotPNL;
        }
        return null;
    }
}