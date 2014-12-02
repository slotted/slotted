package com.googlecode.slotted.gin_codesplitting.client;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;

public interface BaseView extends IsWidget {
    AcceptsOneWidget getSlotDisplay();
}