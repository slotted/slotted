package com.googlecode.slotted.testharness.client.tokenizer;

import com.googlecode.slotted.client.TokenizerParameter;
import com.googlecode.slotted.testharness.client.TestPlace;

abstract public class SuperPlace extends TestPlace {
    @TokenizerParameter
    public String superString;
}
