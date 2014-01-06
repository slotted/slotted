/*
 * Copyright 2012 Jeffrey Kleiss
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.googlecode.slotted.client;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;

import java.util.LinkedList;

/**
 * Represents completed navigation event.  The event is sent after all activities are displayed.
 */
public class NewPlaceEvent extends GwtEvent<NewPlaceEvent.Handler> {
    public static final Type<Handler> Type = new Type<Handler>();

    /**
     * The Handler for the NewPlaceEvent.
     */
    public static interface Handler extends EventHandler {
        /**
         * Called when the navigation is complete.
         *
         * @param newPlaces List of all the Places that are being shown.  All GWT Places will be
         *                  wrapped inside {@link WrappedPlace}.
         */
        void newPlaces(LinkedList<SlottedPlace> newPlaces);
    }

    private LinkedList<SlottedPlace> newPlaces;

    /**
     * Creates a new event.
     *
     * @param newPlaces List of all the places that are being displayed.
     */
    protected NewPlaceEvent(LinkedList<SlottedPlace> newPlaces) {
        this.newPlaces = newPlaces;
    }

    /**
     * @return The type used to register handlers.
     */
    public Type<Handler> getAssociatedType() {
        return Type;
    }

    /**
     * Should only be called by {@link HandlerManager}. In other words, do not use
     * or call.
     *
     * @param handler handler
     */
    protected void dispatch(Handler handler) {
        handler.newPlaces(newPlaces);
    }
}
