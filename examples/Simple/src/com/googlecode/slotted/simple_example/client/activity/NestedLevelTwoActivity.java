package com.googlecode.slotted.simple_example.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HTML;

public class NestedLevelTwoActivity extends AbstractActivity {
    @Override public void start(AcceptsOneWidget panel, com.google.gwt.event.shared.EventBus eventBus) {
        panel.setWidget(new HTML("Nested Level Two View"));
    }
}
