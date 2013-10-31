package com.googlecode.slotted.layout_example.client.ui;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Label;
import com.googlecode.slotted.client.SlottedActivity;

public class Tab1Activity extends SlottedActivity {
    @Override public void start(AcceptsOneWidget slot) {
        slot.setWidget(new Label("Tab1"));
    }
}