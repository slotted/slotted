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

/**
 * Represents Delayed Loading event; either startLoading or stopLoading.
 */
public class LoadingEvent extends GwtEvent<LoadingEvent.Handler> {
    public static final Type<Handler> Type = new Type<Handler>();
    /**
     * Handler for the Loading Events.
     */
    public static interface Handler extends EventHandler {
        /**
         * Called when a {@link SlottedActivity#setLoadingStarted()} is called or SlottedController attempts
         * and fails to load.  This can be called many times during one Activity loading.
         */
        void startLoading();

        /**
         * Called when loading is complete and the Activities are displayed.
         */
        void stopLoading();
    }

    private boolean loading;

    /**
     * Creates an event with the loading state.
     *
     * @param loading true if loading started and false if loading complete.
     */
    protected LoadingEvent(boolean loading) {
        this.loading = loading;
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
        if (loading) {
            handler.startLoading();
        } else {
            handler.stopLoading();
        }
    }
}
