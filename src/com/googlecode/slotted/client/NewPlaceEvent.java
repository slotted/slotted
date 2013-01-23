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
