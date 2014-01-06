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
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

/**
 * Implemented by other objects to control a portion of the UI, and the life cycle is controlled
 * by the {@link SlottedController}.  Extends {@link Activity}, by adding methods to handle the
 * Slotted Place hierarchy, and some convenience methods.
 */
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

    /**
     * Called when the Activity should ready its widget for the user.
     *
     * There are three ways to handle display of Activity's widget.  The first is to set the
     * activity's widget in the panel and call {@setLoadingStarted} which will activate Delayed Loading
     * (https://code.google.com/p/slotted/wiki/DelayedLoading).  Second is to set the activity's widget
     * to display correctly while loading.  Third is to delay the setting the of the activity's widget
     * until it is complete, which will won't prevent the rest of the hierarchy from being displayed.
     *
     * The event bus can be retrieved from {@link #getEventBus()}.
     *
     * @param panel the panel to display this activity's widget
     */
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

    /**
     * Called be SlottedController to get the Widget that will display the child slot.
     *
     * @param slot The child Slot defined in the SlottedPlace.
     * @return The widget that will display the child Activity's widget.  This activity must attach
     * the widget to DOM to be displayed correctly.
     */
    @SuppressWarnings("deprecation")
    public AcceptsOneWidget getChildSlotDisplay(Slot slot) {
        setChildSlotDisplay(slot);
        return slot.getDisplay();
    }

    /**
     * Called by SlottedController pass all the values needed for the convience methods.
     */
    protected void init(SlottedController slottedController, SlottedPlace currentPlace,
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

    /**
     * Called when the SlottedController#goTo() is called and the current activity doesn't change.
     * This happens when a Child Activity is navigated to and it has the same parent, or the goTo() is
     * called with a new Place that equals the existing Place.
     */
    public void onRefresh() {
    }

    /**
     * Called when loading process is complete.  The widget is not added to the DOM until the loading is
     * complete, so this method allows code to be run after the activity's widgets are added to the DOM.
     */
    public void onLoadComplete() {
    }

    /**
     * Called when Activity cache wants to background the activity.  It works similar to mayStop(), where
     * a non null return prevents the navigation from happening.
     *
     * @return null if navigation is okay, error message to display.
     */
    public String mayBackground() {
        return null;
    }

    /**
     * Called when Activity cache backgrounded the activity.  It works similar to onStop(), except the
     * Activity may be redisplayed after onRefresh() is called.  It is possible that a backgrounded Activity
     * will be stopped while backgrounded, at which point, mayStop() and onStop() will be called.
     */
    public void onBackground() {
    }

    /**
     * Activates the Delayed Loading if called inside the {@link #start(AcceptsOneWidget)} method.  If called
     * outside the start(), then a LoadingEvent is sent, but Slotted lifecycle is unaffected.
     */
    public void setLoadingStarted() {
        activeSlot.setLoading(true, this);
    }

    /**
     * Notifies the Delayed Loading code to attempt to display the page.  If another page in the hierarchy is
     * loading, this call will not trigger the widgets to be displayed.  If delayed loading is not active, then a
     * LoadingEvent with be sent stating loading is complete.
     */
    public void setLoadingComplete() {
        activeSlot.setLoading(false, this);
    }

    /**
     * Convenience method for getting the SlottedController
     */
    public SlottedController getSlottedController() {
        return slottedController;
    }

    /**
     * See {@link SlottedController#getCurrentPlace(Class)}
     */
    public <T extends Place> T getCurrentPlace(Class<T> type) {
        return slottedController.getCurrentPlace(type);
    }

    /**
     * See {@link SlottedController#getCurrentActivity(Class)}
     */
    public <T extends Activity> T getCurrentActivity(Class<T> type) {
        return slottedController.getCurrentActivity(type);
    }

    /**
     * See {@link SlottedController#getCurrentActivityByPlace(Class)}
     */
    public Activity getCurrentActivityByPlace(Class<? extends SlottedPlace> type) {
        return slottedController.getCurrentActivityByPlace(type);
    }

    /**
     * Gets the SlottedPlace that created this activity.
     */
    public SlottedPlace getCurrentPlace() {
        return currentPlace;
    }

    /**
     * Convenience method for getting the PlaceParameters which represent all the gobal parameters.
     */
    public PlaceParameters getPlaceParameters() {
        return placeParameters;
    }

    /**
     * Convenience method for getting the EventBus.
     *
     * Any handlers attached to the event bus will be de-registered when
     * the activity is stopped, so activities will rarely need to hold on to the
     * {@link com.google.gwt.event.shared.HandlerRegistration HandlerRegistration}
     * instances returned by {@link EventBus#addHandler}.
     */
    public EventBus getEventBus() {
        return eventBus;
    }

    /**
     * @return The simple class name.
     */
    @Override
    public String toString() {
        return this.getClass().getName().substring(this.getClass().getName().lastIndexOf(".") + 1);
    }
}
