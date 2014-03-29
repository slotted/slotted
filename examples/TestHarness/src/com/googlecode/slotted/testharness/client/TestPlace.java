package com.googlecode.slotted.testharness.client;

import com.google.gwt.activity.shared.Activity;
import com.googlecode.slotted.client.SlottedPlace;

import java.util.HashMap;
import java.util.Map.Entry;

abstract public class TestPlace extends SlottedPlace {
    public static HashMap<SlottedPlace, TestActivity> activityMap = new HashMap<SlottedPlace, TestActivity>();

    public static void resetCounts(SlottedPlace... placeClasses) {
        if (placeClasses.length == 0) {
            for (TestActivity activity: activityMap.values()) {
                activity.resetCounts();
            }
        } else {
            for (SlottedPlace placeClass: placeClasses) {
                TestActivity activity = activityMap.get(placeClass);
                if (activity != null) {
                    activity.resetCounts();
                }
            }
        }
    }

    public static TestActivity getActivity(SlottedPlace placeClass) {
        TestActivity activity = activityMap.get(placeClass);
        if (activity == null) {
            activity = new TestActivity();
            activityMap.put(placeClass, activity);
        }
        return activity;
    }

    public static TestActivity getActivity(Class placeClass) {
        TestActivity activity = null;
        for (Entry<SlottedPlace, TestActivity> entry: activityMap.entrySet()) {
            if (entry.getKey().getClass().equals(placeClass)) {
                assert activity == null : "Can't access by class because there is more than on Activity for that class.  Use Place.";
                activity = entry.getValue();
            }
        }
        return activity;
    }

    @Override final public Activity getActivity() {
        return getActivity(this);
    }
}
