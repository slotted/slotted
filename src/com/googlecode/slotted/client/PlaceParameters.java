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
package com.googlecode.slotted.client;

import java.util.HashMap;
import java.util.Map.Entry;

public class PlaceParameters {
    private HashMap<String, String> paramMap = new HashMap<String, String>();

    public void setParameter(String name, String value) {
        paramMap.put(name, value);
    }

    public String getParameter(String name) {
        return paramMap.get(name);
    }

    public String getRequiredParameter(String name) {
        String param = getParameter(name);
        if (param == null) {
            throw new IllegalStateException("RequiredParam doesn't exist!");
        }
        return param;
    }

    public void addPlaceParameters(PlaceParameters placeParameters) {
        paramMap.putAll(placeParameters.paramMap);
    }

    public String toString() {
        String string = "";
        if (!paramMap.isEmpty()) {
            for (Entry<String, String> entry: paramMap.entrySet()) {
                if (string.isEmpty()) {
                    string += "?";
                } else {
                    string += "&";
                }

                string += entry.getKey() + "=" + entry.getValue();
            }
        }
        return string;
    }



}
