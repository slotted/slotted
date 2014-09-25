package com.googlecode.slotted.client;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.DialogBox;

public class SlottedDialogController extends SlottedController {
    private DialogBox dialogBox;

    public SlottedDialogController(SlottedController slottedController, DialogBox dialogBox, AcceptsOneWidget display) {
        super(slottedController);
        this.dialogBox = dialogBox;
        setDisplay(display);
    }

    @Override protected boolean attemptShowViews() {
        boolean shown = super.attemptShowViews();
        if (shown) {
            dialogBox.show();
        }
        return shown;
    }
}
