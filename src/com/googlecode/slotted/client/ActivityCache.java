package com.googlecode.slotted.client;

import com.google.gwt.activity.shared.Activity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ActivityCache {
    private HashMap<SlottedPlace, Entry> activityCache = new HashMap<SlottedPlace, Entry>();
    private HashMap<SlottedPlace, Entry> usedCache = new HashMap<SlottedPlace, Entry>();
    private HashSet<Class<? extends SlottedPlace>> backgroundMarks = new HashSet<Class<? extends SlottedPlace>>();
    private LinkedList<SlottedPlace> backgroundedActivities = new LinkedList<SlottedPlace>();

    public void add(SlottedPlace place, Activity activity) {
        Entry entry = new Entry(place, activity);
        activityCache.put(place, entry);
        usedCache.put(place, entry);
        backgroundedActivities.remove(place);
    }

    public void clearUnused() {
        activityCache = usedCache;
        usedCache = new HashMap<SlottedPlace, Entry>();
        backgroundMarks = new HashSet<Class<? extends SlottedPlace>>();
    }

    public void removeStopped(Activity activity) {
        Iterator<Entry> valuesIt = activityCache.values().iterator();
        while (valuesIt.hasNext()) {
            Entry entry = valuesIt.next();
            if (activity == entry.activity) {
                valuesIt.remove();
            }
        }
    }

    public Activity getByActivity(Class<? extends Activity> activityClass) {
        for (Entry entry: activityCache.values()) {
            if (activityClass.equals(entry.activity.getClass())) {
                return entry.activity;
            }
        }
        return null;
    }

    public List<Activity> get(Class<? extends SlottedPlace> placeClass) {
        LinkedList<Activity> activities = new LinkedList<Activity>();
        for (Entry entry: activityCache.values()) {
            if (entry.place.getClass().equals(placeClass)) {
                activities.add(entry.activity);
            }
        }

        return activities;
    }

    public Activity get(SlottedPlace place) {
        Entry entry = activityCache.get(place);
        if (entry != null && entry.place.equals(place)) {
            usedCache.put(place, entry);
            return entry.activity;
        }

        return null;
    }

    public void markForBackground(List<Class<? extends SlottedPlace>> placeActivitiesToCache) {
        if (placeActivitiesToCache != null) {
            for (Class<? extends SlottedPlace> placeClass: placeActivitiesToCache) {
                markForBackground(placeClass);
            }
        }
    }

    public void markForBackground(Class<? extends SlottedPlace> placeClass) {
        backgroundMarks.add(placeClass);
        for (Entry entry: activityCache.values()) {
            if (entry.place.getClass().equals(placeClass)) {
                usedCache.put(entry.place, entry);
            }
        }
    }

    public boolean isMarkedForBackground(SlottedPlace place) {
        Class<? extends SlottedPlace> placeClass = place.getClass();
        return backgroundMarks.contains(placeClass);
    }

    public void setBackgrounded(SlottedPlace place) {
        backgroundedActivities.add(place);
    }

    public List<Entry> getBackgroundedActivities(List<Class<? extends SlottedPlace>> includeList) {
        LinkedList<Entry> activities = new LinkedList<Entry>();
        for (SlottedPlace place: backgroundedActivities) {
            if (includeList.contains(place.getClass())) {
                Entry entry = activityCache.get(place);
                if (entry != null) {
                    activities.add(entry);
                }
            }
        }

        return activities;
    }

    public class Entry {
        public SlottedPlace place;
        public Activity activity;

        private Entry(SlottedPlace place, Activity activity) {
            this.place = place;
            this.activity = activity;
        }
    }
}
