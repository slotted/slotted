package com.googlecode.slotted.testharness.client;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.place.shared.Place;
import com.googlecode.slotted.client.SlottedController;
import com.googlecode.slotted.testharness.client.flow.A1aPlace;
import com.googlecode.slotted.testharness.client.flow.APlace;
import com.googlecode.slotted.testharness.client.flow.HomePlace;
import com.googlecode.slotted.testharness.client.tokenizer.BasePlace;
import com.googlecode.slotted.testharness.client.tokenizer.SuperPlace;

public class SlottedControllerTests extends GWTTestCase {
    @Override public String getModuleName() {
        return "com.googlecode.slotted.testharness.TestHarness";
    }

    @Override protected void gwtSetUp() throws Exception {
        super.gwtSetUp();

        TestHarness.startTestHarness();
        TestHarness.slottedController.goTo(new HomePlace());
        TestPlace.resetCounts();
    }

    public void testGetCurrentActivity() {
        TestHarness.slottedController.goTo(new APlace());

        assertNotNull(TestHarness.slottedController.getCurrentActivity(TestActivity.class));
        assertNotNull(TestHarness.slottedController.getCurrentActivityByPlace(APlace.class));
        assertNotNull(TestHarness.slottedController.getCurrentActivityByPlace(A1aPlace.class));

        assertNull(TestHarness.slottedController.getCurrentActivityByPlace(HomePlace.class));

    }

    public void testGetCurrentPlaceSuper() {
        TestHarness.slottedController.goTo(new BasePlace());

        SuperPlace current = TestHarness.slottedController.getCurrentPlace(SuperPlace.class);
        assertNotNull(current);
        assertTrue(current instanceof BasePlace);
    }

    public void testGetCurrentPlaceSlot() {
        TestHarness.slottedController.goTo(new APlace());

        Place current = TestHarness.slottedController.getCurrentPlace(SlottedController.RootSlot);
        assertTrue(current instanceof  APlace);
        Place currentChild = TestHarness.slottedController.getCurrentPlace(APlace.SLOT);
        assertTrue(currentChild instanceof A1aPlace);
    }

}