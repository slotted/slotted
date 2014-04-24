package com.googlecode.slotted.testharness.client;

import com.google.gwt.junit.client.GWTTestCase;
import com.googlecode.slotted.client.SlottedPlace;
import com.googlecode.slotted.testharness.client.flow.HomePlace;
import com.googlecode.slotted.testharness.client.tokenizer.BasePlace;

import java.sql.Timestamp;
import java.util.Date;

public class AutoTokenizerTests extends GWTTestCase {
    @Override public String getModuleName() {
        return "com.googlecode.slotted.testharness.TestHarness";
    }

    @Override protected void gwtSetUp() throws Exception {
        super.gwtSetUp();

        TestHarness.startTestHarness();
        TestHarness.slottedController.goTo(new HomePlace());
        TestPlace.resetCounts();
    }

    @SuppressWarnings("UnusedDeclaration")
    public void testTokensDefault() {
        BasePlace place = new BasePlace();
        String token = TestHarness.slottedController.createToken(place);
        BasePlace clone = TestHarness.slottedController.clonePlace(place);

        assertEquals(place, clone);
        assertEquals(place.superString, clone.superString);
        assertNull(clone.superString);
        assertEquals(place.baseString, clone.baseString);
        assertNull(clone.baseString);
        assertEquals(place.baseByte, clone.baseByte);
        assertEquals(0, clone.baseByte);
        assertEquals(place.baseShort, clone.baseShort);
        assertEquals(0, clone.baseShort);
        assertEquals(place.baseInt, clone.baseInt);
        assertEquals(0, clone.baseInt);
        assertEquals(place.baseFloat, clone.baseFloat);
        assertEquals(0f, clone.baseFloat);
        assertEquals(place.baseDouble, clone.baseDouble);
        assertEquals(0d, clone.baseDouble);
        assertEquals(place.baseBoolean, clone.baseBoolean);
        assertEquals(false, clone.baseBoolean);
        assertEquals(place.baseChar, clone.baseChar);
        assertEquals('\u0000', clone.baseChar);
        assertEquals(place.baseByteObj, clone.baseByteObj);
        assertNull(clone.baseByteObj);
        assertEquals(place.baseShortObj, clone.baseShortObj);
        assertNull(clone.baseShortObj);
        assertEquals(place.baseIntegerObj, clone.baseIntegerObj);
        assertNull(clone.baseIntegerObj);
        assertEquals(place.baseLongObj, clone.baseLongObj);
        assertNull(clone.baseLongObj);
        assertEquals(place.baseFloatObj, clone.baseFloatObj);
        assertNull(clone.baseFloatObj);
        assertEquals(place.baseDoubleObj, clone.baseDoubleObj);
        assertNull(clone.baseDoubleObj);
        assertEquals(place.baseBooleanObj, clone.baseBooleanObj);
        assertNull(clone.baseBooleanObj);
        assertEquals(place.baseCharacterObj, clone.baseCharacterObj);
        assertNull(clone.baseCharacterObj);
        assertEquals(place.baseDate, clone.baseDate);
        assertNull(clone.baseDate);
        assertEquals(place.baseTimestamp, clone.baseTimestamp);
        assertNull(clone.baseTimestamp);
    }

    @SuppressWarnings("UnusedDeclaration")
    public void testTokensValues() {
        BasePlace place = new BasePlace();
        place.superString = "Value";
        place.baseString = "";
        place.baseByte = 1;
        place.baseShort = 1;
        place.baseInt = 1;
        place.baseFloat = 1.2f;
        place.baseDouble = 1.2d;
        place.baseBoolean = true;
        place.baseChar = 'a';
        place.baseByteObj = 1;
        place.baseShortObj = 1;
        place.baseIntegerObj = 1;
        place.baseLongObj = 1L;
        place.baseFloatObj = 1.2f;
        place.baseDoubleObj = 1.2d;
        place.baseBooleanObj = true;
        place.baseCharacterObj = 'a';
        place.baseDate = new Date(1234567890);
        place.baseTimestamp = new Timestamp(1234567890);

        String token = TestHarness.slottedController.createToken(place);
        BasePlace clone = TestHarness.slottedController.clonePlace(place);

        assertEquals(place, clone);
        assertEquals(place.superString, clone.superString);
        assertNotNull(clone.superString);
        assertEquals(place.baseString, clone.baseString);
        assertNotNull(clone.baseString);
        assertEquals(place.baseByte, clone.baseByte);
        assertEquals(place.baseShort, clone.baseShort);
        assertEquals(place.baseInt, clone.baseInt);
        assertEquals(place.baseFloat, clone.baseFloat);
        assertEquals(place.baseDouble, clone.baseDouble);
        assertEquals(place.baseBoolean, clone.baseBoolean);
        assertEquals(place.baseChar, clone.baseChar);
        assertEquals(place.baseByteObj, clone.baseByteObj);
        assertNotNull(clone.baseByteObj);
        assertEquals(place.baseShortObj, clone.baseShortObj);
        assertNotNull(clone.baseShortObj);
        assertEquals(place.baseIntegerObj, clone.baseIntegerObj);
        assertNotNull(clone.baseIntegerObj);
        assertEquals(place.baseLongObj, clone.baseLongObj);
        assertNotNull(clone.baseLongObj);
        assertEquals(place.baseFloatObj, clone.baseFloatObj);
        assertNotNull(clone.baseFloatObj);
        assertEquals(place.baseDoubleObj, clone.baseDoubleObj);
        assertNotNull(clone.baseDoubleObj);
        assertEquals(place.baseBooleanObj, clone.baseBooleanObj);
        assertNotNull(clone.baseBooleanObj);
        assertEquals(place.baseCharacterObj, clone.baseCharacterObj);
        assertNotNull(clone.baseCharacterObj);
        assertEquals(place.baseDate, clone.baseDate);
        assertNotNull(clone.baseDate);
        assertEquals(place.baseTimestamp, clone.baseTimestamp);
        assertNotNull(clone.baseTimestamp);
    }

    @SuppressWarnings("UnusedDeclaration")
    public void testTokensEmpty() {
        SlottedPlace[] places = TestHarness.slottedController.getHistoryMapper().parseToken("Base");
        BasePlace clone = (BasePlace) places[0];

        assertEquals("", clone.superString);
        assertEquals("", clone.baseString);
        assertEquals(0, clone.baseByte);
        assertEquals(0, clone.baseShort);
        assertEquals(0, clone.baseInt);
        assertEquals(0f, clone.baseFloat);
        assertEquals(0d, clone.baseDouble);
        assertEquals(false, clone.baseBoolean);
        assertEquals('\u0000', clone.baseChar);
        assertNull(clone.baseByteObj);
        assertNull(clone.baseShortObj);
        assertNull(clone.baseIntegerObj);
        assertNull(clone.baseLongObj);
        assertNull(clone.baseFloatObj);
        assertNull(clone.baseDoubleObj);
        assertNull(clone.baseBooleanObj);
        assertNull(clone.baseCharacterObj);
        assertNull(clone.baseDate);
        assertNull(clone.baseTimestamp);
    }

}