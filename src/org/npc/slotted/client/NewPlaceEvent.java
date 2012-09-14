package org.npc.slotted.client;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class NewPlaceEvent extends GwtEvent<NewPlaceEvent.Handler> {
    public static final Type<Handler> Type = new Type<Handler>();
    public static interface Handler extends EventHandler {
        /**
         * @return true if this method completely handled the error, which will prevent the default
         *         exception handling from running.
         */
        void newPlace(SlottedPlace newPlace, SlottedPlace[] nonDefaultPlaces);
    }

    private SlottedPlace newPlace;
    private SlottedPlace[] nonDefaultPlaces;

    public NewPlaceEvent(SlottedPlace newPlace, SlottedPlace[] nonDefaultPlaces) {
        this.newPlace = newPlace;
        this.nonDefaultPlaces = nonDefaultPlaces;
    }

    public Type<Handler> getAssociatedType() {
        return Type;
    }

    protected void dispatch(Handler handler) {
        handler.newPlace(newPlace, nonDefaultPlaces);
    }
}
