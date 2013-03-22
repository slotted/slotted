package com.googlecode.slotted.testharness.client.activity;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.slotted.client.SlottedPlace;

public class GoToActivity extends TestActivity {

    public SlottedPlace goToPlace = null;

    /**
     * Invoked by the SlottedController to refresh an Activity.  This is also called when
     * an Activity is started and doesn't override start().
     */
    @Override
    public void start(AcceptsOneWidget panel) {
        if (goToPlace != null) {
            getSlottedController().goTo(goToPlace);
            goToPlace = null;
        }

        super.start(panel);
    }

    @Override public void onRefresh() {
        if (goToPlace != null) {
            getSlottedController().goTo(goToPlace);
            goToPlace = null;
        }

        super.onRefresh();
    }
}
