package com.googlecode.slotted.testharness.client;

import com.google.gwt.junit.client.GWTTestCase;
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
    public void testTokens() {
        BasePlace place = new BasePlace(2, new Date(), new Timestamp(System.currentTimeMillis()));
        String token = TestHarness.slottedController.createToken(place);
        BasePlace clone = TestHarness.slottedController.clonePlace(place);

        assertEquals(place, clone);
        assertEquals(place.superString, clone.superString);
        assertEquals(place.baseString, clone.baseString);
        assertEquals(place.baseInt, clone.baseInt);
        assertEquals(place.baseDouble, clone.baseDouble);
        assertEquals(place.baseBoolean, clone.baseBoolean);
        assertEquals(place.baseDate, clone.baseDate);
        assertEquals(place.baseTimestamp, clone.baseTimestamp);
        assertNotNull(clone.baseDate);
        assertNotNull(clone.baseTimestamp);
    }
}