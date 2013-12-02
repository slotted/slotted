package com.googlecode.slotted.client;

import com.google.gwt.activity.shared.Activity;

import java.util.HashMap;
import java.util.HashSet;

public class ActivityCache {
    private HashMap<Class<? extends SlottedPlace>, Entry> activityCache = new HashMap<Class<? extends SlottedPlace>, Entry>();
    private HashMap<Class<? extends SlottedPlace>, Entry> usedCache = new HashMap<Class<? extends SlottedPlace>, Entry>();
    private HashSet<Class<? extends SlottedPlace>> backgroundMarks = new HashSet<Class<? extends SlottedPlace>>();

    public void clearUnused() {
        activityCache = usedCache;
        usedCache = new HashMap<Class<? extends SlottedPlace>, Entry>();
        backgroundMarks = new HashSet<Class<? extends SlottedPlace>>();
    }

    public Activity getByActivity(Class<? extends Activity> activityClass) {
        for (Entry entry: activityCache.values()) {
            if (activityClass.equals(entry.activity.getClass())) {
                return entry.activity;
            }
        }
        return null;
    }

    public Activity get(Class<? extends SlottedPlace> placeClass) {
        Entry entry = activityCache.get(placeClass);
        if (entry != null) {
            usedCache.put(placeClass, entry);
            return entry.activity;
        }

        return null;
    }

    public Activity get(SlottedPlace place) {
        Class<? extends SlottedPlace> placeClass = place.getClass();
        Entry entry = activityCache.get(placeClass);
        if (entry != null && entry.place.equals(place)) {
            usedCache.put(placeClass, entry);
            return entry.activity;
        }

        return null;
    }

    public Activity markForBackground(Class<? extends SlottedPlace> placeClass) {
        Entry entry = activityCache.get(placeClass);
        if (entry != null) {
            usedCache.put(placeClass, entry);
            backgroundMarks.add(placeClass);
            return entry.activity;
        }

        return null;
    }

    public boolean isMarkedForBackground(SlottedPlace place) {
        Class<? extends SlottedPlace> placeClass = place.getClass();
        return backgroundMarks.contains(placeClass);
    }

    public void add(SlottedPlace place, Activity activity) {
        Class<? extends SlottedPlace> placeClass = place.getClass();
        Entry entry = new Entry(place, activity);
        activityCache.put(placeClass, entry);
        usedCache.put(placeClass, entry);
    }

    private class Entry {
        public SlottedPlace place;
        public Activity activity;

        private Entry(SlottedPlace place, Activity activity) {
            this.place = place;
            this.activity = activity;
        }
    }
}
