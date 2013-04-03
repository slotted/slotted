package com.googlecode.slotted.gap2_example.client;

import com.google.gwt.place.shared.Prefix;
import com.googlecode.slotted.client.GlobalParameter;
import com.googlecode.slotted.client.MappedSlottedPlace;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedController;
import com.googlecode.slotted.client.SlottedTokenizer;
import com.googlecode.slotted.client.TokenizerParameter;

@Prefix("b")
public class BasePlace extends MappedSlottedPlace {
    public static final Slot SLOT = new Slot(new BasePlace(123, "gval"), new HelloPlace("Base!"));

    @TokenizerParameter
    private int tokenNum;
    @GlobalParameter
    private String global;

    public BasePlace() {
    }

    public BasePlace(int tokenNum, String global) {
        this.tokenNum = tokenNum;
        this.global = global;
    }

    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {SLOT};
    }

    public static interface Tokenizer extends SlottedTokenizer<BasePlace>{}
}
