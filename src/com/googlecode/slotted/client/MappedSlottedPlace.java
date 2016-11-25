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

/**
 * A SlottedPlace that uses the ActivityMapper in GWT's A&amp;P framework to handle the
 * creation of the Activity.
 */
abstract public class MappedSlottedPlace extends SlottedPlace {

    /**
     * Overrides the getActivity(), so it doesn't need to be implemented.  But Slotted never calls
     * this method for classes of this type.
     */
    @Override final public Activity getActivity() {
        return null;
    }
}
