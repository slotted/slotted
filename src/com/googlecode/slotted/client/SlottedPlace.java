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

//todo javadoc
abstract public class SlottedPlace extends Place implements HasParameters {

    private String[] equalsParameterNames = new String[0];
    private PlaceParameters placeParameters = new PlaceParameters();
    private LinkedList<String> setKeys;

    abstract public Slot getParentSlot();
    abstract public Slot[] getChildSlots();
    abstract public Activity getActivity();

    public void setParameter(String name, String value) {
        if (placeParameters == null) {
            placeParameters = new PlaceParameters();
            setKeys = new LinkedList<String>();
        }

        placeParameters.setParameter(name, value);
        setKeys.add(name);
    }

    public String getParameter(String name) {
        if (placeParameters != null) {
            return placeParameters.getParameter(name);
        }
        return null;
    }

    protected void setEqualsParameters(String... parameterName) {
        equalsParameterNames = parameterName;
    }

    @Override public void extractParameters(PlaceParameters intoPlaceParameters) {
        if (this.placeParameters != null) {
            intoPlaceParameters.addPlaceParameters(this.placeParameters, setKeys);
        }
    }

    @Override public void setPlaceParameters(PlaceParameters placeParameters) {
        this.placeParameters = placeParameters;
        setKeys = new LinkedList<String>();
    }

    public PlaceParameters getPlaceParameters() {
        return placeParameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
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

    @Override
    public int hashCode() {
        int result = getClass().hashCode();
        for (String name: equalsParameterNames) {
            String value = getParameter(name);
            result = 31 * result + (value != null ? value.hashCode() : 0);
        }

        return result;
    }

    @Override public String toString() {
        return this.getClass().getName().substring(this.getClass().getName().lastIndexOf(".")+1);
    }
}
