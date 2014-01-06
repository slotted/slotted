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

/**
 * Indicates this object handles Global Parameters.  SlottedPlace handles global parameters, but this interface can
 * be added to a standard Place to allow it to handle global parameters.
 */
public interface HasParameters {
    /**
     * Called by Slotted framework have the Place add all the global parameters is has to the PlaceParameters.
     *
     * @param placeParameters Where the global parameters should be stored.
     */
    public void extractParameters(PlaceParameters placeParameters);

    /**
     * Called by Slotted framework to set the Global parameters used in this request.
     *
     * @param placeParameters The global parameters for the request.
     */
    public void setPlaceParameters(PlaceParameters placeParameters);
}
