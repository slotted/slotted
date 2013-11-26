package com.googlecode.slotted.testharness.client;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedActivity;
import com.googlecode.slotted.client.SlottedPlace;

import java.util.HashMap;

public class TestActivity extends SlottedActivity {

    public boolean isStartLoading = false;
    public boolean isShowDisplay = true;
    public TestDisplay testDisplay;
    public AcceptsOneWidget panel;
    public HashMap<Slot, SimplePanel> childDisplay;

    public int setChildSlotDisplayCount;
    public int startCount;
    public int onStopCount;
    public int onCancelCount;
    public int onRefreshCount;
    public int onLoadCompleteCount;
    public int onBackgroundCount;
    public int mayStopCount;

    public void resetCounts() {
        setChildSlotDisplayCount = 0;
        startCount = 0;
        onStopCount = 0;
        onCancelCount = 0;
        onRefreshCount = 0;
        mayStopCount = 0;
        onLoadCompleteCount = 0;
        onBackgroundCount = 0;
    }

    @Override public void start(AcceptsOneWidget panel) {
        startCount++;
        this.panel = panel;
        SlottedPlace place = getCurrentPlace();

        testDisplay = new TestDisplay();
        testDisplay.addStyleName("display");
        testDisplay.add(new HTML(place.toString()));

        Slot[] childSlots = place.getChildSlots();
        if (childSlots != null && childSlots.length > 0) {
            childDisplay = new HashMap<Slot, SimplePanel>();
            for (Slot slot: childSlots) {
                SimplePanel childPanel = new SimplePanel();
                childDisplay.put(slot, childPanel);
                testDisplay.add(childPanel);
            }

        } else {
            childDisplay = null;
        }

        if (isStartLoading) {
            setLoadingStarted();
        }
        if (isShowDisplay) {
            showDisplay();
        }
    }

    public void showDisplay() {
        panel.setWidget(testDisplay);
    }

    @Override public void setChildSlotDisplay(Slot slot) {
        setChildSlotDisplayCount++;

        slot.setDisplay(childDisplay.get(slot));
    }


    @Override public void onCancel() {
        onCancelCount++;
    }


    @Override public void onRefresh() {
        onRefreshCount++;
    }

    @Override public void onStop() {
        onStopCount++;
    }

    @Override public String mayStop() {
        mayStopCount++;
        return super.mayStop();
    }

    @Override public void onLoadComplete() {
        onLoadCompleteCount++;
        super.onLoadComplete();
    }

    @Override public void onBackground() {
        onBackgroundCount++;
        super.onBackground();
    }
}
