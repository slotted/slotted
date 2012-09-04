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

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

abstract public class SlottedActivity extends AbstractActivity {

    private SlottedController slottedController;
    private SlottedPlace currentPlace;
    private PlaceParameters placeParameters;
    private EventBus eventBus;

    /**
     * Replaces the legacy activity that uses the old legacy EventBus.
     */
    public void start(AcceptsOneWidget panel, com.google.gwt.event.shared.EventBus eventBus) {
        start(panel);
    }

    abstract public void start(AcceptsOneWidget panel);

    public void setChildSlotDisplay(Slot slot) {
        throw new UnsupportedOperationException(this + " doesn't support child slots.  Make sure " +
                "the SlottedActivity overrides setChildSlotDisplay()");
    }


    public void init(SlottedController slottedController, SlottedPlace currentPlace,
            PlaceParameters placeParameters, EventBus eventBus)
    {
        this.slottedController = slottedController;
        this.currentPlace = currentPlace;
        this.placeParameters = placeParameters;
        this.eventBus = eventBus;
    }

    public SlottedController getSlottedController() {
        return slottedController;
    }

    public SlottedPlace getCurrentPlace() {
        return currentPlace;
    }

    public PlaceParameters getPlaceParameters() {
        return placeParameters;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    @Override
    public String toString() {
        return this.getClass().getName().substring(this.getClass().getName().lastIndexOf(".") + 1);
    }
}
