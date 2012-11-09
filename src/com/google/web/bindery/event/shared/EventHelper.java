package com.google.web.bindery.event.shared;

public class EventHelper {
    public static void setSource(Event event, Object source) {
        event.setSource(source);
    }

    public static <H> void dispatch(Event<H> event, H handler) {
        event.dispatch(handler);
    }
}
