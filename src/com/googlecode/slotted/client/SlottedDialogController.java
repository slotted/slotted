package com.googlecode.slotted.client;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.web.bindery.event.shared.ResettableEventBus;

public class SlottedDialogController extends SlottedController {
    private PopupPanel popupPanel;

    public SlottedDialogController(SlottedController slottedController, PopupPanel popupPanel, AcceptsOneWidget display) {
        super(slottedController.getHistoryMapper(), new ResettableEventBus(slottedController.getEventBus()), slottedController.getDelegate(), false);
        setNavigationOverride(slottedController.getNavigationOverride());
        this.codeSplitMap.putAll(slottedController.codeSplitMap);
        this.popupPanel = popupPanel;
        setDisplay(display);

        popupPanel.addCloseHandler(new CloseHandler<PopupPanel>() {
            @Override public void onClose(CloseEvent<PopupPanel> event) {
                ((ResettableEventBus)getEventBus()).removeHandlers();
            }
        });

    }

    @Override protected boolean attemptShowViews() {
        boolean shown = super.attemptShowViews();
        if (shown) {
            popupPanel.show();
        }
        return shown;
    }

    @Deprecated
    public PopupPanel getDialogBox() {
        return popupPanel;
    }

    public PopupPanel getPopupPanel() {
        return popupPanel;
    }
}
