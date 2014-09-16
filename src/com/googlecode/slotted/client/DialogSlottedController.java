package com.googlecode.slotted.client;

import com.googlecode.slotted.client.SlottedDialogHandler.DialogStruct;

public class DialogSlottedController extends SlottedController {
    private DialogStruct dialogStruct;

    public DialogSlottedController(SlottedController slottedController, SlottedDialogHandler dialogHandler) {
        super(slottedController);

        dialogStruct = new DialogStruct();
        dialogHandler.createDialog(dialogStruct);

        setDisplay(dialogStruct.dialogRootPanel);
    }

    @Override protected boolean attemptShowViews() {
        boolean shown = super.attemptShowViews();
        if (shown) {
            dialogStruct.dialog.show();
        }
        return shown;
    }
}
