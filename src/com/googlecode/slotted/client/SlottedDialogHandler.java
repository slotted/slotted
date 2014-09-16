package com.googlecode.slotted.client;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.DialogBox;

public interface SlottedDialogHandler {
    public void createDialog(DialogStruct dialogStruct);

    public static class DialogStruct {
        public DialogBox dialog;
        public AcceptsOneWidget dialogRootPanel;
    }
}
