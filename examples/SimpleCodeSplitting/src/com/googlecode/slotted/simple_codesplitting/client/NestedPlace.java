package com.googlecode.slotted.simple_codesplitting.client;

import com.google.gwt.core.client.GWT;
import com.googlecode.slotted.client.CodeSplitActivity;
import com.googlecode.slotted.client.CodeSplitGroup;
import com.googlecode.slotted.client.CodeSplitPlace;
import com.googlecode.slotted.client.Slot;

public class NestedPlace extends CodeSplitPlace {
    public static final Slot SLOT = new Slot(new NestedPlace(), new NestedLevelTwoPlace());

    @Override public Slot getParentSlot() {
        return ParentPlace.SLOT;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {SLOT};
    }

    @CodeSplitActivity(NestedActivity.class)
    @Override public CodeSplitGroup getCodeSplitGroup() {
        return GWT.create(NestedGroup.class);
    }
}
