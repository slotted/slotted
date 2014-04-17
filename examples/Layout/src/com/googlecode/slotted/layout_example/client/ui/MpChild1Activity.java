package com.googlecode.slotted.layout_example.client.ui;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.googlecode.slotted.client.SlottedActivity;

public class MpChild1Activity extends SlottedActivity {
    private SimpleLayoutPanel slotPanel;

    @Override public void start(AcceptsOneWidget containerWidget) {
        containerWidget.setWidget(createUi());
    }

    private IsWidget createUi() {
        return new Label("Multi Child 1");
    }
}