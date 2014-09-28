package com.googlecode.slotted.client;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.web.bindery.event.shared.ResettableEventBus;

public class SlottedDialogController extends SlottedController {
    private DialogBox dialogBox;

    public SlottedDialogController(SlottedController slottedController, DialogBox dialogBox, AcceptsOneWidget display) {
        super(slottedController.getHistoryMapper(), new ResettableEventBus(slottedController.getEventBus()), slottedController.getDelegate(), false);
        setNavigationOverride(slottedController.getNavigationOverride());
        this.dialogBox = dialogBox;
        setDisplay(display);

        dialogBox.addCloseHandler(new CloseHandler<PopupPanel>() {
            @Override public void onClose(CloseEvent<PopupPanel> event) {
                ((ResettableEventBus)getEventBus()).removeHandlers();
            }
        });

    }

    @Override protected boolean attemptShowViews() {
        boolean shown = super.attemptShowViews();
        if (shown) {
            dialogBox.show();
        }
        return shown;
    }

    public DialogBox getDialogBox() {
        return dialogBox;
    }
}
