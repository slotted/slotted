package com.googlecode.slotted.layout_example.client.header_leftbar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.slotted.client.SlottedActivity;

public class Content2Activity extends SlottedActivity {
    interface MyUiBinder extends UiBinder<Widget, Content2Activity> {}
    private static MyUiBinder ourUiBinder = GWT.create(MyUiBinder.class);

    @Override public void start(AcceptsOneWidget panel) {
        panel.setWidget(ourUiBinder.createAndBindUi(this));
    }
}
