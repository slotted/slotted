package com.googlecode.slotted.gin_codesplitting.client;

import com.googlecode.slotted.client.CodeSplitMapper;
import com.googlecode.slotted.client.CodeSplitPlace;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedController;

public class BasePlace extends CodeSplitPlace {
    public static final Slot SLOT = new Slot(new BasePlace(), new HelloPlace("Base!"));

    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {SLOT};
    }

    @Override public CodeSplitMapper getCodeSplitGroup() {
        return null;
        //return AppGinjector.instance.getBaseActivity();
    }
}
