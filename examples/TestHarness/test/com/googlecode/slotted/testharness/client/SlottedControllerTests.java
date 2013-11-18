package com.googlecode.slotted.testharness.client;

import com.google.gwt.junit.client.GWTTestCase;
import com.googlecode.slotted.testharness.client.flow.A1aPlace;
import com.googlecode.slotted.testharness.client.flow.APlace;
import com.googlecode.slotted.testharness.client.flow.HomePlace;

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

}