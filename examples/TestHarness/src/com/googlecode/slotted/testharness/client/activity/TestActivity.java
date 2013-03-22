package com.googlecode.slotted.testharness.client.activity;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedActivity;
import com.googlecode.slotted.client.SlottedPlace;

import java.util.HashMap;

public class TestActivity extends SlottedActivity {

    public VerticalPanel display;
    public HashMap<Slot, SimplePanel> childDisplay;

    public int setChildSlotDisplayCount;
    public int startCount;
    public int onStopCount;
    public int onCancelCount;
    public int onRefreshCount;
    public int mayStopCount;


    public void resetCounts() {
        setChildSlotDisplayCount = 0;
        startCount = 0;
        onStopCount = 0;
        onCancelCount = 0;
        onRefreshCount = 0;
        mayStopCount = 0;
    }

    @Override public void start(AcceptsOneWidget panel) {
        startCount++;
        SlottedPlace place = getCurrentPlace();

        display = new VerticalPanel();
        display.addStyleName("display");
        panel.setWidget(display);
        display.add(new HTML(place.toString()));

        Slot[] childSlots = place.getChildSlots();
        if (childSlots != null && childSlots.length > 0) {
            childDisplay = new HashMap<Slot, SimplePanel>();
            for (Slot slot: childSlots) {
                SimplePanel childPanel = new SimplePanel();
                childDisplay.put(slot, childPanel);
                display.add(childPanel);
            }

        } else {
            childDisplay = null;
        }
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
}
