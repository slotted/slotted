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

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

//todo javadoc
abstract public class SlottedActivity extends AbstractActivity {

    private SlottedController slottedController;
    private SlottedPlace currentPlace;
    private PlaceParameters placeParameters;
    private EventBus eventBus;
    private ActiveSlot activeSlot;

    /**
     * Replaces the legacy activity that uses the old legacy EventBus.
     */
    public void start(AcceptsOneWidget panel, com.google.gwt.event.shared.EventBus eventBus) {
        start(panel);
    }

    abstract public void start(AcceptsOneWidget panel);


    /**
     * @deprecated
     *
     * Use {@link #getChildSlotDisplay(Slot)} instead.
     */
    public void setChildSlotDisplay(Slot slot) {
        throw new UnsupportedOperationException(this + " doesn't support child slots.  Make sure " +
                "the SlottedActivity overrides getChildSlotDisplay()");
    }

    public AcceptsOneWidget getChildSlotDisplay(Slot slot) {
        setChildSlotDisplay(slot);
        return slot.getDisplay();
    }

    public void init(SlottedController slottedController, SlottedPlace currentPlace,
            PlaceParameters placeParameters, EventBus eventBus, ActiveSlot activeSlot)
    {
        this.slottedController = slottedController;
        this.currentPlace = currentPlace;
        this.placeParameters = placeParameters;
        this.eventBus = eventBus;
        this.activeSlot = activeSlot;
    }

    /**
     * On Cancel is called when the ActiveSlot display widget doesn't receive setWidget() call.
     * SlottedActivity overrides the default behaviour by calling onStop().  This was done because
     * it is believed that onCancel() is not normally implemented, and there is a potential bug if
     * only onStop() is implemented.
     */
    @Override public void onCancel() {
        onStop();
    }

    public void onRefresh() {
    }

    public void setLoadingStarted() {
        activeSlot.setLoading(true, this);
    }

    public void setLoadingComplete() {
        activeSlot.setLoading(false, this);
    }

    public SlottedController getSlottedController() {
        return slottedController;
    }

    public <T> T getCurrentPlace(Class<T> type) {
        return slottedController.getCurrentPlace(type);
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
