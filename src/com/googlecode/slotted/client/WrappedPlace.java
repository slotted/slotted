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

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class WrappedPlace extends SlottedPlace {
    private Place place;
    private ActivityMapper activityMapper;

    protected WrappedPlace(Place place, ActivityMapper activityMapper) {
        this.place = place;
        this.activityMapper = activityMapper;
    }

    public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    public Slot[] getChildSlots() {
        return null;
    }

    public Activity getActivity() {
        return activityMapper.getActivity(place);
    }

    public Place getPlace() {
        return place;
    }

    @Override public void storeParameters(PlaceParameters placeParameters) {
        if (place instanceof HasParameters) {
            ((HasParameters) place).storeParameters(placeParameters);
        }
    }

    @Override public void retrieveParameters(PlaceParameters placeParameters) {
        if (place instanceof HasParameters) {
            ((HasParameters) place).retrieveParameters(placeParameters);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WrappedPlace that = (WrappedPlace) o;

        if (!place.equals(that.place)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return place.hashCode();
    }
}


