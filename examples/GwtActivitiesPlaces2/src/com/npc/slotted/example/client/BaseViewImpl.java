package com.npc.slotted.example.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class BaseViewImpl extends Composite implements BaseView {
    interface TheUiBinder extends UiBinder<Widget, BaseViewImpl> {}
    private static TheUiBinder uiBinder = GWT.create(TheUiBinder.class);
    interface HelloViewImplUiBinder extends UiBinder {}

    @UiField SimplePanel slotPanel;

    public BaseViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override public AcceptsOneWidget getSlotDisplay() {
        return slotPanel;
    }
}