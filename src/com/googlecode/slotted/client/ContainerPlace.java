package com.googlecode.slotted.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

/**
 * A SlottedPlace that is a logical grouping but doesn't have any visual effects.  This is often used with
 * Activity Cache, or menu component that needs a logical place to determine highlighting.
 */
abstract public class ContainerPlace extends SlottedPlace {
    /**
     * Creates an Activity that uses its starting Display for it children, which will allow the container to have
     * no visual effect.
     */
    @Override public final Activity getActivity() {
        return new SlottedActivity() {
            private AcceptsOneWidget slotWidget;

            @Override public void start(AcceptsOneWidget panel) {
                slotWidget = panel;
            }

            @Override public AcceptsOneWidget getChildSlotDisplay(Slot slot) {
                return slotWidget;
            }
        };
    }
}
