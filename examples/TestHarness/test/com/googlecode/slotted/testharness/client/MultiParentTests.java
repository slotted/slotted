package com.googlecode.slotted.testharness.client;

import com.google.gwt.junit.client.GWTTestCase;
import com.googlecode.slotted.testharness.client.flow.HomePlace;
import com.googlecode.slotted.testharness.client.multi_parent.Child1Place;
import com.googlecode.slotted.testharness.client.multi_parent.Child2Place;
import com.googlecode.slotted.testharness.client.multi_parent.MultiPlace;
import com.googlecode.slotted.testharness.client.multi_parent.Parent1Child1Place;
import com.googlecode.slotted.testharness.client.multi_parent.Parent1Place;
import com.googlecode.slotted.testharness.client.multi_parent.Parent2Child1Place;
import com.googlecode.slotted.testharness.client.multi_parent.Parent2Place;

public class MultiParentTests extends GWTTestCase {
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
    public void test() {
        // No existing parent - Child goes to first parent
        TestHarness.slottedController.goTo(new Child1Place());
        assertNotNull(TestHarness.slottedController.getCurrentPlace(Parent1Place.class));
        assertNull(TestHarness.slottedController.getCurrentPlace(Parent2Place.class));

        // Parent1 exists - Child stays under that parent
        TestHarness.slottedController.goTo(new Parent1Child1Place());
        TestHarness.slottedController.goTo(new Child2Place());
        assertNotNull(TestHarness.slottedController.getCurrentPlace(Parent1Place.class));
        assertNull(TestHarness.slottedController.getCurrentPlace(Parent2Place.class));

        // Parent2 exists - Child stays under that parent
        TestHarness.slottedController.goTo(new Parent2Child1Place());
        TestHarness.slottedController.goTo(new Child2Place());
        assertNull(TestHarness.slottedController.getCurrentPlace(Parent1Place.class));
        assertNotNull(TestHarness.slottedController.getCurrentPlace(Parent2Place.class));

        // Parent1 exists with Parent2 non default - Child stays under that parent
        TestHarness.slottedController.goTo(new Parent1Child1Place());
        TestHarness.slottedController.goTo(new Child2Place(), new Parent2Place());
        assertNull(TestHarness.slottedController.getCurrentPlace(Parent1Place.class));
        assertNotNull(TestHarness.slottedController.getCurrentPlace(Parent2Place.class));

        // Parent2 exists with Parent1 non default Same Place
        TestActivity child2Activity = TestPlace.getActivity(Child2Place.class);
        child2Activity.resetCounts();
        TestHarness.slottedController.goTo(new Child2Place(), new Parent1Place());
        assertNotNull(TestHarness.slottedController.getCurrentPlace(Parent1Place.class));
        assertNull(TestHarness.slottedController.getCurrentPlace(Parent2Place.class));
        assertEquals(1, child2Activity.startCount);
        assertEquals(1, child2Activity.mayStopCount);
        assertEquals(1, child2Activity.onStopCount);
        assertEquals(0, child2Activity.onRefreshCount);

        TestHarness.slottedController.goTo(new MultiPlace(), new Parent2Place());
        assertNull(TestHarness.slottedController.getCurrentPlace(Parent1Place.class));
        assertNotNull(TestHarness.slottedController.getCurrentPlace(Parent2Place.class));

        TestHarness.slottedController.goTo(new MultiPlace(), new Parent1Place());
        assertNotNull(TestHarness.slottedController.getCurrentPlace(Parent1Place.class));
        assertNull(TestHarness.slottedController.getCurrentPlace(Parent2Place.class));

        TestHarness.slottedController.goTo(new MultiPlace(Parent2Place.slot));
        assertNull(TestHarness.slottedController.getCurrentPlace(Parent1Place.class));
        assertNotNull(TestHarness.slottedController.getCurrentPlace(Parent2Place.class));

        TestHarness.slottedController.goTo(new MultiPlace(Parent1Place.slot));
        assertNotNull(TestHarness.slottedController.getCurrentPlace(Parent1Place.class));
        assertNull(TestHarness.slottedController.getCurrentPlace(Parent2Place.class));

    }

    public void testBug() {
        TestHarness.slottedController.goTo(new Parent2Child1Place());
        TestHarness.slottedController.goTo(new Child1Place());
        TestHarness.slottedController.goTo(new Child2Place());
        assertNull(TestHarness.slottedController.getCurrentPlace(Parent1Place.class));
        assertNotNull(TestHarness.slottedController.getCurrentPlace(Parent2Place.class));
    }


}