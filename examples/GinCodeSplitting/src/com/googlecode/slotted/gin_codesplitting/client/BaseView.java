package com.googlecode.slotted.gin_codesplitting.client;

import javax.inject.Singleton;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;

@Singleton
public interface BaseView extends IsWidget {
    AcceptsOneWidget getSlotDisplay();
}