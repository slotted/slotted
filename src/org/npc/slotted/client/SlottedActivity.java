/*
 * Copyright 2012 NPC Unlimited
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
package org.npc.slotted.client;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public interface SlottedActivity {

    /**
     * Called when the user is trying to navigate away from this activity.
     *
     * @return A message to display to the user, e.g. to warn of unsaved work, or
     *         null to say nothing
     */
    String mayStop();

    /**
     * Called when the Activity's widget has been removed from view. All event
     * handlers it registered will have been removed before this method is called.
     */
    void onStop();

    /**
     * Called when the Activity should ready its widget for the user. When the
     * widget is ready (typically after an RPC response has been received),
     * receiver should present it by calling
     * {@link AcceptsOneWidget#setWidget} on the given panel.
     * <p>
     * Any handlers attached to the provided event bus will be de-registered when
     * the activity is stopped, so activities will rarely need to hold on to the
     * {@link com.google.gwt.event.shared.HandlerRegistration HandlerRegistration}
     * instances returned by {@link EventBus#addHandler}.
     *
     * @param panel the panel to display this activity's widget when it is ready
     * @param eventBus the event bus
     */
    void start(AcceptsOneWidget panel, PlaceParameters params, EventBus eventBus);

    void refresh(AcceptsOneWidget panel, PlaceParameters parameters, EventBus eventBus);

    void setChildSlotDisplay(Slot slot);

}
