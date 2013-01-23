package com.googlecode.slotted.client;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import java.util.LinkedList;

public class NewPlaceEvent extends GwtEvent<NewPlaceEvent.Handler> {
    public static final Type<Handler> Type = new Type<Handler>();
    public static interface Handler extends EventHandler {
        /**
         * @return true if this method completely handled the error, which will prevent the default
         *         exception handling from running.
         */
        void newPlaces(LinkedList<SlottedPlace> newPlaces);
    }

    private LinkedList<SlottedPlace> newPlaces;

    public NewPlaceEvent(LinkedList<SlottedPlace> newPlaces) {
        this.newPlaces = newPlaces;
    }

    public Type<Handler> getAssociatedType() {
        return Type;
    }

    protected void dispatch(Handler handler) {
        handler.newPlaces(newPlaces);
    }
}
