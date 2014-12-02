package com.googlecode.slotted.simple_codesplitting.client;

import com.google.gwt.core.client.GWT;
import com.googlecode.slotted.client.CodeSplitActivity;
import com.googlecode.slotted.client.CodeSplitGroup;
import com.googlecode.slotted.client.CodeSplitPlace;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedController;

public class ParentPlace extends CodeSplitPlace {
    public static final Slot SLOT = new Slot(new ParentPlace(), new NestedPlace());

    public ParentPlace() {
        super();
    }

    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {SLOT};
    }

    @CodeSplitActivity(ParentActivity.class)
    @Override public CodeSplitGroup getCodeSplitGroup() {
        return GWT.create(NestedGroup.class);
    }
}
