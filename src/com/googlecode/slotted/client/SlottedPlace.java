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
import com.google.gwt.place.shared.Place;

import java.util.LinkedList;

/**
 * Defines how this place fits in the Place Hierarchy, and what Activity will be displayed for
 * the part of the hierarchy represented by this Place.
 *
 * The SlottedPlace must define the Slot which it will be displayed in.  This allows the parent hierarchy
 * to be created when only a child is displayed.  I must provide a list of child Slots that will need populated
 * by Place/Activities.
 */
abstract public class SlottedPlace extends Place implements HasParameters {

    private String[] equalsParameterNames = new String[0];
    private PlaceParameters placeParameters = new PlaceParameters();
    private LinkedList<String> setKeys = new LinkedList<String>();

    /**
     * Gets the slot that this Place is displayed in.  A Place can only be associated to one Slot, but it is
     * possible to create another Place, which represents the same activity in another part of the hierarchy.
     *
     * @return It is best if this is a static instance created in the Parent Place.
     */
    abstract public Slot getParentSlot();

    /**
     * Gets of the list of all child slots that should be controlled by Slotted.  Best practice is to define
     * the Slots as static instances in the Place.
     *
     * @return Array of all the child slots.
     */
    abstract public Slot[] getChildSlots();

    /**
     * Gets the Activity instance that should be used for this navigation.  It is recommend to create a new
     * instance every time this method is called.  If the Activity needs to last longer than the single call,
     * look into the Activity Caching (https://code.google.com/p/slotted/wiki/ActivityCaching), before using
     * a Singleton pattern, which consume to much memory.
     *
     * @return The activity responsible for creating the UI.
     */
    abstract public Activity getActivity();

    /**
     * Used to set a global parameter if AutoTokenizer is not used.
     *
     * @param name The name key for the global parameter
     * @param value The value of the parameter.  A null will be represented as a empty String.
     */
    public void setParameter(String name, String value) {
        placeParameters.setParameter(name, value);
        setKeys.add(name);
    }

    /**
     * Gets the global parameter when the AutoTokenizer is not used.
     *
     * @param name The name key
     * @return The value of the global parameter.  Null means the parameter wasn't set.
     */
    public String getParameter(String name) {
        return placeParameters.getParameter(name);
    }

    /**
     * AutoTokenizer is the preferred method of having parameters included in equals()
     */
    @Deprecated
    protected void setEqualsParameters(String... parameterName) {
        equalsParameterNames = parameterName;
    }

    /**
     * Called by Slotted framework to extract the global parameters associated with this Place.
     *
     * @param intoPlaceParameters The location that the parameters to added to.
     */
    @Override public void extractParameters(PlaceParameters intoPlaceParameters) {
        intoPlaceParameters.addPlaceParameters(this.placeParameters, setKeys);
    }

    /**
     * Called by the Slotted framework to sync the global parameters for all Places.
     *
     * @param placeParameters The global parameters for the request.
     */
    @Override public void setPlaceParameters(PlaceParameters placeParameters) {
        this.placeParameters = placeParameters;
    }

    /**
     * Gets the object that contains the global parameters.
     */
    public PlaceParameters getPlaceParameters() {
        return placeParameters;
    }

    /**
     * If AutoTokenizer is used, the equals will all annotated variables to determine if the objects are equal.
     */
    @SuppressWarnings("unchecked") @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AutoTokenizer tokenizer = AutoTokenizer.tokenizers.get(this.getClass());
        if (tokenizer != null && !tokenizer.equals(this, (Place) o)) {
            return false;
        }

        SlottedPlace place = (SlottedPlace) o;
        for (String name: equalsParameterNames) {
            String value = getParameter(name);
            String placeValue = place.getParameter(name);
            if (value != null ? !value.equals(placeValue) : placeValue != null) {
                return false;
            }
        }

        return true;
    }

    /**
     * Simple hashcode based on the Class, because it is believed that this won't be used much.
     */
    @Override
    public int hashCode() {
        int result = getClass().hashCode();
        for (String name: equalsParameterNames) {
            String value = getParameter(name);
            result = 31 * result + (value != null ? value.hashCode() : 0);
        }

        return result;
    }

    /**
     * @return The simple name of the class.
     */
    @Override public String toString() {
        return this.getClass().getName().substring(this.getClass().getName().lastIndexOf(".")+1);
    }
}
