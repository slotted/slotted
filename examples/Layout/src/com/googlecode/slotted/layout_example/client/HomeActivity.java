package com.googlecode.slotted.layout_example.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.slotted.client.SlottedActivity;
import com.googlecode.slotted.layout_example.client.header_leftbar.HeaderPlace;

public class HomeActivity extends SlottedActivity {
    interface MyUiBinder extends UiBinder<Widget, HomeActivity> {}
    private static MyUiBinder ourUiBinder = GWT.create(MyUiBinder.class);
    @UiField Hyperlink headLeftbarButton;

    @Override public void start(AcceptsOneWidget panel) {
        panel.setWidget(ourUiBinder.createAndBindUi(this));

        headLeftbarButton.setTargetHistoryToken(getSlottedController().createSimpleToken(new HeaderPlace()));
    }
}
