package org.npc.slotted.client;

import com.google.gwt.activity.shared.Activity;

/**
 * A SlottedPlace that uses the ActivityMapper in GWT's A&P framework to handle the
 * creation of the Activity.
 */
abstract public class MappedSlottedPlace extends SlottedPlace {
    @Override final public Activity getActivity() {
        return null;
    }
}
