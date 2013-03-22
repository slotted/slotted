package com.googlecode.slotted.testharness.client;

import com.google.gwt.junit.client.GWTTestCase;
import com.googlecode.slotted.client.SlottedPlace;
import com.googlecode.slotted.testharness.client.activity.A1a1aPlace;
import com.googlecode.slotted.testharness.client.activity.A1aPlace;
import com.googlecode.slotted.testharness.client.activity.APlace;
import com.googlecode.slotted.testharness.client.activity.B1aPlace;
import com.googlecode.slotted.testharness.client.activity.B1bPlace;
import com.googlecode.slotted.testharness.client.activity.B2aPlace;
import com.googlecode.slotted.testharness.client.activity.BPlace;
import com.googlecode.slotted.testharness.client.activity.GoTo1aPlace;
import com.googlecode.slotted.testharness.client.activity.GoTo1bPlace;
import com.googlecode.slotted.testharness.client.activity.GoTo2aPlace;
import com.googlecode.slotted.testharness.client.activity.GoTo2bPlace;
import com.googlecode.slotted.testharness.client.activity.GoToActivity;
import com.googlecode.slotted.testharness.client.activity.GoToPlace;
import com.googlecode.slotted.testharness.client.activity.HomePlace;
import com.googlecode.slotted.testharness.client.activity.OnCancelPlace;
import com.googlecode.slotted.testharness.client.activity.TestActivity;
import com.googlecode.slotted.testharness.client.activity.TestPlace;

public class FlowTests extends GWTTestCase {
    @Override public String getModuleName() {
        return "com.googlecode.slotted.testharness.TestHarness";
    }

    @Override protected void gwtSetUp() throws Exception {
        super.gwtSetUp();

        TestHarness.startTestHarness();
        TestHarness.slottedController.goTo(new HomePlace());
        TestPlace.resetCounts();
    }

    public void testBasicStartUp() {
        assertNotNull(TestHarness.slottedController);
    }

    public void testBasicHierarchyConstruction() {
        TestHarness.slottedController.goTo(new APlace());

        TestActivity aActivity = TestPlace.getActivity(APlace.class);
        assertEquals(1, aActivity.setChildSlotDisplayCount);
        assertEquals(1, aActivity.startCount);
        assertEquals(0, aActivity.mayStopCount);
        assertEquals(0, aActivity.onCancelCount);
        assertEquals(0, aActivity.onStopCount);
        assertEquals(0, aActivity.onRefreshCount);

        TestActivity a1aActivity = TestPlace.getActivity(A1aPlace.class);
        assertEquals(1, a1aActivity.setChildSlotDisplayCount);
        assertEquals(1, a1aActivity.startCount);
        assertEquals(0, a1aActivity.mayStopCount);
        assertEquals(0, a1aActivity.onCancelCount);
        assertEquals(0, a1aActivity.onStopCount);
        assertEquals(0, a1aActivity.onRefreshCount);

        TestActivity a1a1aActivity = TestPlace.getActivity(A1a1aPlace.class);
        assertEquals(0, a1a1aActivity.setChildSlotDisplayCount);
        assertEquals(1, a1a1aActivity.startCount);
        assertEquals(0, a1a1aActivity.mayStopCount);
        assertEquals(0, a1a1aActivity.onCancelCount);
        assertEquals(0, a1a1aActivity.onStopCount);
        assertEquals(0, a1a1aActivity.onRefreshCount);

        TestPlace.resetCounts();
        TestHarness.slottedController.goTo(new A1aPlace());

        aActivity = TestPlace.getActivity(APlace.class);
        assertEquals(0, aActivity.setChildSlotDisplayCount);
        assertEquals(0, aActivity.startCount);
        assertEquals(0, aActivity.mayStopCount);
        assertEquals(0, aActivity.onCancelCount);
        assertEquals(0, aActivity.onStopCount);
        assertEquals(1, aActivity.onRefreshCount);

        a1aActivity = TestPlace.getActivity(A1aPlace.class);
        assertEquals(0, a1aActivity.setChildSlotDisplayCount);
        assertEquals(0, a1aActivity.startCount);
        assertEquals(0, a1aActivity.mayStopCount);
        assertEquals(0, a1aActivity.onCancelCount);
        assertEquals(0, a1aActivity.onStopCount);
        assertEquals(1, a1aActivity.onRefreshCount);

        a1a1aActivity = TestPlace.getActivity(A1a1aPlace.class);
        assertEquals(0, a1a1aActivity.setChildSlotDisplayCount);
        assertEquals(0, a1a1aActivity.startCount);
        assertEquals(0, a1a1aActivity.mayStopCount);
        assertEquals(0, a1a1aActivity.onCancelCount);
        assertEquals(0, a1a1aActivity.onStopCount);
        assertEquals(1, a1a1aActivity.onRefreshCount);

        TestPlace.resetCounts();
        TestHarness.slottedController.goTo(new A1aPlace(), new SlottedPlace[0], true);

        aActivity = TestPlace.getActivity(APlace.class);
        assertEquals(1, aActivity.setChildSlotDisplayCount);
        assertEquals(1, aActivity.startCount);
        assertEquals(1, aActivity.mayStopCount);
        assertEquals(0, aActivity.onCancelCount);
        assertEquals(1, aActivity.onStopCount);
        assertEquals(0, aActivity.onRefreshCount);

        a1aActivity = TestPlace.getActivity(A1aPlace.class);
        assertEquals(1, a1aActivity.setChildSlotDisplayCount);
        assertEquals(1, a1aActivity.startCount);
        assertEquals(1, a1aActivity.mayStopCount);
        assertEquals(0, a1aActivity.onCancelCount);
        assertEquals(1, a1aActivity.onStopCount);
        assertEquals(0, a1aActivity.onRefreshCount);

        a1a1aActivity = TestPlace.getActivity(A1a1aPlace.class);
        assertEquals(0, a1a1aActivity.setChildSlotDisplayCount);
        assertEquals(1, a1a1aActivity.startCount);
        assertEquals(1, a1a1aActivity.mayStopCount);
        assertEquals(0, a1a1aActivity.onCancelCount);
        assertEquals(1, a1a1aActivity.onStopCount);
        assertEquals(0, a1a1aActivity.onRefreshCount);

        TestPlace.resetCounts();
        TestHarness.slottedController.goTo(new HomePlace());

        aActivity = TestPlace.getActivity(APlace.class);
        assertEquals(0, aActivity.setChildSlotDisplayCount);
        assertEquals(0, aActivity.startCount);
        assertEquals(1, aActivity.mayStopCount);
        assertEquals(0, aActivity.onCancelCount);
        assertEquals(1, aActivity.onStopCount);
        assertEquals(0, aActivity.onRefreshCount);

        a1aActivity = TestPlace.getActivity(A1aPlace.class);
        assertEquals(0, a1aActivity.setChildSlotDisplayCount);
        assertEquals(0, a1aActivity.startCount);
        assertEquals(1, a1aActivity.mayStopCount);
        assertEquals(0, a1aActivity.onCancelCount);
        assertEquals(1, a1aActivity.onStopCount);
        assertEquals(0, a1aActivity.onRefreshCount);

        a1a1aActivity = TestPlace.getActivity(A1a1aPlace.class);
        assertEquals(0, a1a1aActivity.setChildSlotDisplayCount);
        assertEquals(0, a1a1aActivity.startCount);
        assertEquals(1, a1a1aActivity.mayStopCount);
        assertEquals(0, a1a1aActivity.onCancelCount);
        assertEquals(1, a1a1aActivity.onStopCount);
        assertEquals(0, a1a1aActivity.onRefreshCount);

    }

    public void testGoToChild() {
        TestHarness.slottedController.goTo(new A1a1aPlace());

        TestActivity aActivity = TestPlace.getActivity(APlace.class);
        assertEquals(1, aActivity.setChildSlotDisplayCount);
        assertEquals(1, aActivity.startCount);
        assertEquals(0, aActivity.mayStopCount);
        assertEquals(0, aActivity.onCancelCount);
        assertEquals(0, aActivity.onStopCount);
        assertEquals(0, aActivity.onRefreshCount);

        TestActivity a1aActivity = TestPlace.getActivity(A1aPlace.class);
        assertEquals(1, a1aActivity.setChildSlotDisplayCount);
        assertEquals(1, a1aActivity.startCount);
        assertEquals(0, a1aActivity.mayStopCount);
        assertEquals(0, a1aActivity.onCancelCount);
        assertEquals(0, a1aActivity.onStopCount);
        assertEquals(0, a1aActivity.onRefreshCount);

        TestActivity a1a1aActivity = TestPlace.getActivity(A1a1aPlace.class);
        assertEquals(0, a1a1aActivity.setChildSlotDisplayCount);
        assertEquals(1, a1a1aActivity.startCount);
        assertEquals(0, a1a1aActivity.mayStopCount);
        assertEquals(0, a1a1aActivity.onCancelCount);
        assertEquals(0, a1a1aActivity.onStopCount);
        assertEquals(0, a1a1aActivity.onRefreshCount);
    }

    public void test2SlotConstruction() {
        TestHarness.slottedController.goTo(new BPlace());

        TestActivity bActivity = TestPlace.getActivity(BPlace.class);
        assertEquals(2, bActivity.setChildSlotDisplayCount);
        assertEquals(1, bActivity.startCount);
        assertEquals(0, bActivity.mayStopCount);
        assertEquals(0, bActivity.onCancelCount);
        assertEquals(0, bActivity.onStopCount);
        assertEquals(0, bActivity.onRefreshCount);

        TestActivity b1aActivity = TestPlace.getActivity(B1aPlace.class);
        assertEquals(0, b1aActivity.setChildSlotDisplayCount);
        assertEquals(1, b1aActivity.startCount);
        assertEquals(0, b1aActivity.mayStopCount);
        assertEquals(0, b1aActivity.onCancelCount);
        assertEquals(0, b1aActivity.onStopCount);
        assertEquals(0, b1aActivity.onRefreshCount);

        TestActivity b1bActivity = TestPlace.getActivity(B1bPlace.class);
        assertEquals(0, b1bActivity.setChildSlotDisplayCount);
        assertEquals(0, b1bActivity.startCount);
        assertEquals(0, b1bActivity.mayStopCount);
        assertEquals(0, b1bActivity.onCancelCount);
        assertEquals(0, b1bActivity.onStopCount);
        assertEquals(0, b1bActivity.onRefreshCount);

        TestActivity b2aActivity = TestPlace.getActivity(B2aPlace.class);
        assertEquals(0, b2aActivity.setChildSlotDisplayCount);
        assertEquals(1, b2aActivity.startCount);
        assertEquals(0, b2aActivity.mayStopCount);
        assertEquals(0, b2aActivity.onCancelCount);
        assertEquals(0, b2aActivity.onStopCount);
        assertEquals(0, b2aActivity.onRefreshCount);

        TestPlace.resetCounts();
        TestHarness.slottedController.goTo(new B1bPlace());

        bActivity = TestPlace.getActivity(BPlace.class);
        assertEquals(0, bActivity.setChildSlotDisplayCount);
        assertEquals(0, bActivity.startCount);
        assertEquals(0, bActivity.mayStopCount);
        assertEquals(0, bActivity.onCancelCount);
        assertEquals(0, bActivity.onStopCount);
        assertEquals(1, bActivity.onRefreshCount);

        b1bActivity = TestPlace.getActivity(B1aPlace.class);
        assertEquals(0, b1bActivity.setChildSlotDisplayCount);
        assertEquals(0, b1bActivity.startCount);
        assertEquals(1, b1bActivity.mayStopCount);
        assertEquals(0, b1bActivity.onCancelCount);
        assertEquals(1, b1bActivity.onStopCount);
        assertEquals(0, b1bActivity.onRefreshCount);

        b1bActivity = TestPlace.getActivity(B1bPlace.class);
        assertEquals(0, b1bActivity.setChildSlotDisplayCount);
        assertEquals(1, b1bActivity.startCount);
        assertEquals(0, b1bActivity.mayStopCount);
        assertEquals(0, b1bActivity.onCancelCount);
        assertEquals(0, b1bActivity.onStopCount);
        assertEquals(0, b1bActivity.onRefreshCount);

        b2aActivity = TestPlace.getActivity(B2aPlace.class);
        assertEquals(0, b2aActivity.setChildSlotDisplayCount);
        assertEquals(0, b2aActivity.startCount);
        assertEquals(0, b2aActivity.mayStopCount);
        assertEquals(0, b2aActivity.onCancelCount);
        assertEquals(0, b2aActivity.onStopCount);
        assertEquals(1, b2aActivity.onRefreshCount);

        TestPlace.resetCounts();
        TestHarness.slottedController.goTo(new B2aPlace());

        bActivity = TestPlace.getActivity(BPlace.class);
        assertEquals(0, bActivity.setChildSlotDisplayCount);
        assertEquals(0, bActivity.startCount);
        assertEquals(0, bActivity.mayStopCount);
        assertEquals(0, bActivity.onCancelCount);
        assertEquals(0, bActivity.onStopCount);
        assertEquals(1, bActivity.onRefreshCount);

        b1bActivity = TestPlace.getActivity(B1aPlace.class);
        assertEquals(0, b1bActivity.setChildSlotDisplayCount);
        assertEquals(0, b1bActivity.startCount);
        assertEquals(0, b1bActivity.mayStopCount);
        assertEquals(0, b1bActivity.onCancelCount);
        assertEquals(0, b1bActivity.onStopCount);
        assertEquals(0, b1bActivity.onRefreshCount);

        b1bActivity = TestPlace.getActivity(B1bPlace.class);
        assertEquals(0, b1bActivity.setChildSlotDisplayCount);
        assertEquals(0, b1bActivity.startCount);
        assertEquals(0, b1bActivity.mayStopCount);
        assertEquals(0, b1bActivity.onCancelCount);
        assertEquals(0, b1bActivity.onStopCount);
        assertEquals(1, b1bActivity.onRefreshCount);

        b2aActivity = TestPlace.getActivity(B2aPlace.class);
        assertEquals(0, b2aActivity.setChildSlotDisplayCount);
        assertEquals(0, b2aActivity.startCount);
        assertEquals(0, b2aActivity.mayStopCount);
        assertEquals(0, b2aActivity.onCancelCount);
        assertEquals(0, b2aActivity.onStopCount);
        assertEquals(1, b2aActivity.onRefreshCount);

    }

    public void testOnCancel() {
        TestHarness.slottedController.goTo(new OnCancelPlace());

        TestActivity activity = OnCancelPlace.activity;
        assertEquals(0, activity.setChildSlotDisplayCount);
        assertEquals(1, activity.startCount);
        assertEquals(0, activity.mayStopCount);
        assertEquals(0, activity.onCancelCount);
        assertEquals(0, activity.onStopCount);
        assertEquals(0, activity.onRefreshCount);

        activity.resetCounts();
        TestHarness.slottedController.goTo(new HomePlace());

        assertEquals(0, activity.setChildSlotDisplayCount);
        assertEquals(0, activity.startCount);
        assertEquals(1, activity.mayStopCount);
        assertEquals(1, activity.onCancelCount);
        assertEquals(0, activity.onStopCount);
        assertEquals(0, activity.onRefreshCount);
    }

    public void testGoToWhileGoTo() {
        TestHarness.slottedController.goTo(new GoTo2bPlace());

        GoToActivity goToActivity = GoToPlace.activity;
        assertEquals(2, goToActivity.setChildSlotDisplayCount);
        assertEquals(1, goToActivity.startCount);
        assertEquals(0, goToActivity.mayStopCount);
        assertEquals(0, goToActivity.onCancelCount);
        assertEquals(0, goToActivity.onStopCount);
        assertEquals(0, goToActivity.onRefreshCount);

        TestActivity goTo1aActivity = TestPlace.getActivity(GoTo1aPlace.class);
        assertEquals(0, goTo1aActivity.setChildSlotDisplayCount);
        assertEquals(1, goTo1aActivity.startCount);
        assertEquals(0, goTo1aActivity.mayStopCount);
        assertEquals(0, goTo1aActivity.onCancelCount);
        assertEquals(0, goTo1aActivity.onStopCount);
        assertEquals(0, goTo1aActivity.onRefreshCount);

        TestActivity goTo1bActivity = TestPlace.getActivity(GoTo1bPlace.class);
        assertEquals(0, goTo1bActivity.setChildSlotDisplayCount);
        assertEquals(0, goTo1bActivity.startCount);
        assertEquals(0, goTo1bActivity.mayStopCount);
        assertEquals(0, goTo1bActivity.onCancelCount);
        assertEquals(0, goTo1bActivity.onStopCount);
        assertEquals(0, goTo1bActivity.onRefreshCount);

        TestActivity goTo2aActivity = TestPlace.getActivity(GoTo2aPlace.class);
        assertEquals(0, goTo2aActivity.setChildSlotDisplayCount);
        assertEquals(0, goTo2aActivity.startCount);
        assertEquals(0, goTo2aActivity.mayStopCount);
        assertEquals(0, goTo2aActivity.onCancelCount);
        assertEquals(0, goTo2aActivity.onStopCount);
        assertEquals(0, goTo2aActivity.onRefreshCount);

        TestActivity goTo2bActivity = TestPlace.getActivity(GoTo2bPlace.class);
        assertEquals(0, goTo2bActivity.setChildSlotDisplayCount);
        assertEquals(1, goTo2bActivity.startCount);
        assertEquals(0, goTo2bActivity.mayStopCount);
        assertEquals(0, goTo2bActivity.onCancelCount);
        assertEquals(0, goTo2bActivity.onStopCount);
        assertEquals(0, goTo2bActivity.onRefreshCount);


        goToActivity.resetCounts();
        TestPlace.resetCounts();

        goToActivity.goToPlace = new GoTo1aPlace();

        TestHarness.slottedController.goTo(new GoTo1bPlace());

        goToActivity = GoToPlace.activity;
        assertEquals(0, goToActivity.setChildSlotDisplayCount);
        assertEquals(0, goToActivity.startCount);
        assertEquals(0, goToActivity.mayStopCount);
        assertEquals(0, goToActivity.onCancelCount);
        assertEquals(0, goToActivity.onStopCount);
        assertEquals(2, goToActivity.onRefreshCount);

        goTo1aActivity = TestPlace.getActivity(GoTo1aPlace.class);
        assertEquals(0, goTo1aActivity.setChildSlotDisplayCount);
        assertEquals(1, goTo1aActivity.startCount);
        assertEquals(1, goTo1aActivity.mayStopCount);
        assertEquals(0, goTo1aActivity.onCancelCount);
        assertEquals(1, goTo1aActivity.onStopCount);
        assertEquals(0, goTo1aActivity.onRefreshCount);

        goTo1bActivity = TestPlace.getActivity(GoTo1bPlace.class);
        assertEquals(0, goTo1bActivity.setChildSlotDisplayCount);
        assertEquals(0, goTo1bActivity.startCount);
        assertEquals(0, goTo1bActivity.mayStopCount);
        assertEquals(0, goTo1bActivity.onCancelCount);
        assertEquals(0, goTo1bActivity.onStopCount);
        assertEquals(0, goTo1bActivity.onRefreshCount);

        goTo2aActivity = TestPlace.getActivity(GoTo2aPlace.class);
        assertEquals(0, goTo2aActivity.setChildSlotDisplayCount);
        assertEquals(0, goTo2aActivity.startCount);
        assertEquals(0, goTo2aActivity.mayStopCount);
        assertEquals(0, goTo2aActivity.onCancelCount);
        assertEquals(0, goTo2aActivity.onStopCount);
        assertEquals(0, goTo2aActivity.onRefreshCount);

        goTo2bActivity = TestPlace.getActivity(GoTo2bPlace.class);
        assertEquals(0, goTo2bActivity.setChildSlotDisplayCount);
        assertEquals(0, goTo2bActivity.startCount);
        assertEquals(0, goTo2bActivity.mayStopCount);
        assertEquals(0, goTo2bActivity.onCancelCount);
        assertEquals(0, goTo2bActivity.onStopCount);
        assertEquals(1, goTo2bActivity.onRefreshCount);

    }
}
