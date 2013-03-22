package com.googlecode.slotted.testharness.client;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TestDisplay extends VerticalPanel {
    private boolean displayed;

    public TestDisplay() {
        displayed = false;
    }

    @Override public Widget asWidget() {
        displayed = true;
        return super.asWidget();
    }

    public boolean isDisplayed() {
        return displayed;
    }
}
