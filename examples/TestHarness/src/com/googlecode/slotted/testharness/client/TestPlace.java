package com.googlecode.slotted.testharness.client;

import com.google.gwt.activity.shared.Activity;
import com.googlecode.slotted.client.SlottedPlace;

import java.util.HashMap;

abstract public class TestPlace extends SlottedPlace {
    public static HashMap<Class, TestActivity> activityMap = new HashMap<Class, TestActivity>();

    public static void resetCounts(Class... placeClasses) {
        if (placeClasses.length == 0) {
            for (TestActivity activity: activityMap.values()) {
                activity.resetCounts();
            }
        } else {
            for (Class placeClass: placeClasses) {
                TestActivity activity = activityMap.get(placeClass);
                if (activity != null) {
                    activity.resetCounts();
                }
            }
        }
    }

    public static TestActivity getActivity(Class placeClass) {
        TestActivity activity = activityMap.get(placeClass);
        if (activity == null) {
            activity = new TestActivity();
            activityMap.put(placeClass, activity);
        }
        return activity;
    }

    @Override final public Activity getActivity() {
        return getActivity(this.getClass());
    }
}
