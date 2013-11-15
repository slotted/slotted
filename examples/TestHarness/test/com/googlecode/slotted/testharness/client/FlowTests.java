package com.googlecode.slotted.testharness.client;

import com.google.gwt.junit.client.GWTTestCase;
import com.googlecode.slotted.client.LoadingEvent;
import com.googlecode.slotted.client.PlaceParameters;
import com.googlecode.slotted.client.SlottedPlace;
import com.googlecode.slotted.testharness.client.flow.A1a1aPlace;
import com.googlecode.slotted.testharness.client.flow.A1aPlace;
import com.googlecode.slotted.testharness.client.flow.A1b1aPlace;
import com.googlecode.slotted.testharness.client.flow.A1b1bPlace;
import com.googlecode.slotted.testharness.client.flow.A1bPlace;
import com.googlecode.slotted.testharness.client.flow.APlace;
import com.googlecode.slotted.testharness.client.flow.B1aPlace;
import com.googlecode.slotted.testharness.client.flow.B1bPlace;
import com.googlecode.slotted.testharness.client.flow.B2aPlace;
import com.googlecode.slotted.testharness.client.flow.BPlace;
import com.googlecode.slotted.testharness.client.flow.GParam1aPlace;
import com.googlecode.slotted.testharness.client.flow.GParam1bPlace;
import com.googlecode.slotted.testharness.client.flow.GoTo1aPlace;
import com.googlecode.slotted.testharness.client.flow.GoTo1bPlace;
import com.googlecode.slotted.testharness.client.flow.GoTo2aPlace;
import com.googlecode.slotted.testharness.client.flow.GoTo2bPlace;
import com.googlecode.slotted.testharness.client.flow.GoToActivity;
import com.googlecode.slotted.testharness.client.flow.GoToPlace;
import com.googlecode.slotted.testharness.client.flow.HomePlace;
import com.googlecode.slotted.testharness.client.flow.Loading1aPlace;
import com.googlecode.slotted.testharness.client.flow.LoadingPlace;
import com.googlecode.slotted.testharness.client.flow.OnCancelPlace;

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

    public void testNonDefaults() {
        TestHarness.slottedController.goTo(new BPlace(), new B1bPlace());

        TestActivity bActivity = TestPlace.getActivity(BPlace.class);
        assertEquals(2, bActivity.setChildSlotDisplayCount); //2 child slots
        assertEquals(1, bActivity.startCount);
        assertEquals(0, bActivity.mayStopCount);
        assertEquals(0, bActivity.onCancelCount);
        assertEquals(0, bActivity.onStopCount);
        assertEquals(0, bActivity.onRefreshCount);

        TestActivity b1bActivity = TestPlace.getActivity(B1bPlace.class);
        assertEquals(0, b1bActivity.setChildSlotDisplayCount);
        assertEquals(1, b1bActivity.startCount);
        assertEquals(0, b1bActivity.mayStopCount);
        assertEquals(0, b1bActivity.onCancelCount);
        assertEquals(0, b1bActivity.onStopCount);
        assertEquals(0, b1bActivity.onRefreshCount);

        TestActivity b1aActivity = TestPlace.getActivity(B1aPlace.class);
        assertEquals(0, b1aActivity.setChildSlotDisplayCount);
        assertEquals(0, b1aActivity.startCount);
        assertEquals(0, b1aActivity.mayStopCount);
        assertEquals(0, b1aActivity.onCancelCount);
        assertEquals(0, b1aActivity.onStopCount);
        assertEquals(0, b1aActivity.onRefreshCount);
    }

    public void testGoToToken() {
        String token = TestHarness.slottedController.createToken(new A1b1bPlace());
        TestHarness.slottedController.goTo(token);

        TestActivity bActivity = TestPlace.getActivity(APlace.class);
        assertEquals(1, bActivity.startCount);

        TestActivity b1bActivity = TestPlace.getActivity(A1bPlace.class);
        assertEquals(1, b1bActivity.startCount);

        TestActivity b1aActivity = TestPlace.getActivity(A1b1aPlace.class);
        assertEquals(1, b1aActivity.startCount);
    }

    public void test2SlotConstruction() {
        TestHarness.slottedController.goTo(new BPlace(2));

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

    public void testGlobalParams() {
        TestHarness.slottedController.goTo(new GParam1aPlace());

        PlaceParameters params = TestHarness.slottedController.getCurrentParameters();
        assertEquals("GParam1a", params.getParameter("global"));
        assertEquals("set", params.getParameter("GParam"));
        assertEquals("set", params.getParameter("GParam1a"));
        assertEquals(null, params.getParameter("GParam1b"));
        assertEquals("set", params.getParameter("GParam2a"));

        TestHarness.slottedController.goTo(new GParam1bPlace());

        params = TestHarness.slottedController.getCurrentParameters();
        assertEquals("GParam1b", params.getParameter("global"));
        assertEquals("set", params.getParameter("GParam"));
        assertEquals(null, params.getParameter("GParam1a"));
        assertEquals("set", params.getParameter("GParam1b"));
        assertEquals("set", params.getParameter("GParam2a"));
    }

    class LoadingHandler implements LoadingEvent.Handler {
        public int startCount = 0;
        public int stopCount = 0;

        @Override public void startLoading() {
            startCount++;
        }

        @Override public void stopLoading() {
            stopCount++;
        }
    }

    public void testLoadingDelayedSetWidget() {
        LoadingHandler loadingHandler = new LoadingHandler();
        TestHarness.slottedController.getEventBus().addHandler(LoadingEvent.Type, loadingHandler);

        TestActivity loadingActivity = TestPlace.getActivity(LoadingPlace.class);
        TestActivity loading1aActivity = TestPlace.getActivity(Loading1aPlace.class);
        loading1aActivity.isStartLoading = false;
        loading1aActivity.isShowDisplay = false;

        TestHarness.slottedController.goTo(new LoadingPlace());

        assertTrue(loadingActivity.testDisplay.isDisplayed());
        assertFalse(loading1aActivity.testDisplay.isDisplayed());
        assertEquals(0, loadingHandler.startCount);
        assertEquals(0, loadingHandler.stopCount);

        loading1aActivity.showDisplay();

        assertTrue(loading1aActivity.testDisplay.isDisplayed());
        assertEquals(1, loadingHandler.startCount);
        assertEquals(1, loadingHandler.stopCount);
    }

    public void testLoadingStartLoading() {
        LoadingHandler loadingHandler = new LoadingHandler();
        TestHarness.slottedController.getEventBus().addHandler(LoadingEvent.Type, loadingHandler);

        TestActivity loadingActivity = TestPlace.getActivity(LoadingPlace.class);
        TestActivity loading1aActivity = TestPlace.getActivity(Loading1aPlace.class);
        loading1aActivity.isStartLoading = true;
        loading1aActivity.isShowDisplay = true;

        TestHarness.slottedController.goTo(new LoadingPlace());

        assertFalse(loadingActivity.testDisplay.isDisplayed());
        assertFalse(loading1aActivity.testDisplay.isDisplayed());
        assertEquals(1, loadingHandler.startCount);
        assertEquals(0, loadingHandler.stopCount);

        loading1aActivity.setLoadingComplete();

        assertTrue(loadingActivity.testDisplay.isDisplayed());
        assertTrue(loading1aActivity.testDisplay.isDisplayed());
        assertEquals(1, loadingHandler.startCount);
        assertEquals(1, loadingHandler.stopCount);

    }

    public void testLoadingOnCancel() {
        LoadingHandler loadingHandler = new LoadingHandler();
        TestHarness.slottedController.getEventBus().addHandler(LoadingEvent.Type, loadingHandler);

        TestActivity loadingActivity = TestPlace.getActivity(LoadingPlace.class);
        TestActivity loading1aActivity = TestPlace.getActivity(Loading1aPlace.class);
        loading1aActivity.isStartLoading = true;
        loading1aActivity.isShowDisplay = true;

        TestHarness.slottedController.goTo(new LoadingPlace());
        loadingActivity.resetCounts();
        loading1aActivity.resetCounts();

        TestHarness.slottedController.goTo(new HomePlace());

        assertEquals(0, loadingActivity.setChildSlotDisplayCount);
        assertEquals(0, loadingActivity.startCount);
        assertEquals(1, loadingActivity.mayStopCount);
        assertEquals(0, loadingActivity.onCancelCount);
        assertEquals(1, loadingActivity.onStopCount);
        assertEquals(0, loadingActivity.onRefreshCount);

        assertEquals(0, loading1aActivity.setChildSlotDisplayCount);
        assertEquals(0, loading1aActivity.startCount);
        assertEquals(1, loading1aActivity.mayStopCount);
        assertEquals(1, loading1aActivity.onCancelCount);
        assertEquals(0, loading1aActivity.onStopCount);
        assertEquals(0, loading1aActivity.onRefreshCount);
    }


}