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

import java.util.ArrayList;

//todo javadoc
public interface NavigationOverride {
    /**
     * Called for every place request.  If override is desired return new list of places, otherwise
     * return the passed places.
     *
     * @param requestedPlaces List of places with the first item being the requested place, and the
     *                        other are non default places.
     * @return The places that should be navigated to.  Null is not allowed.
     */
    ArrayList<SlottedPlace> checkOverrides(ArrayList<SlottedPlace> requestedPlaces);
}
