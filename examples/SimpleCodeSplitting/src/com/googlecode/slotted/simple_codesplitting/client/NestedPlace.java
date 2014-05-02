package com.googlecode.slotted.simple_codesplitting.client;

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

    @Override public CodeSplitGroup getCodeSplitGroup() {
        return new GroupNested();
    }
}
