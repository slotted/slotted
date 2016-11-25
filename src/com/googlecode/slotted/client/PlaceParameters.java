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

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Logger;

/**
 * Holds global parameters that aren't associated with a specific Place.  When a place is constructed,
 * it has its own instance of PlaceParameters, but during navigation, all Places will receive the same
 * instance that has all the global parameters for all Places. Two Places setting the same global parameter,
 * one will overwrite the others value.  It is the responsibility of the developer to avoid this conflict, or
 * use {@link TokenizerParameter} which associates values to a specific place.
 */
public class PlaceParameters {
    private static final Logger log = Logger.getLogger(SlottedController.class.getName());
    private HashMap<String, String> paramMap = new HashMap<String, String>();

    /**
     * Same as {@link #set(String, String)}
     */
    public void setParameter(String name, String value) {
        set(name, value);
    }

    /**
     * Sets the parameter by key/value pair.
     *
     * @param name The name or key of the parameter
     * @param value The value, which will be converted to a String. 'null' will become an empty String ("").
     */
    public void set(String name, String value) {
        if (value == null ) {
            value = "";
        }
        paramMap.put(name, value);
    }

    /**
     * Sets the parameter by key/value pair.
     *
     * @param name The name or key of the parameter
     * @param value The value, which will be converted to a String.
     */
    public void set(String name, int value) {
        paramMap.put(name, "" + value);
    }

    /**
     * Sets the parameter by key/value pair.
     *
     * @param name The name or key of the parameter
     * @param value The value, which will be converted to a String.
     */
    public void set(String name, long value) {
        paramMap.put(name, "" + value);
    }

    /**
     * Sets the parameter by key/value pair.
     *
     * @param name The name or key of the parameter
     * @param value The value, which will be converted to a String.
     */
    public void set(String name, float value) {
        paramMap.put(name, "" + value);
    }

    /**
     * Sets the parameter by key/value pair.
     *
     * @param name The name or key of the parameter
     * @param value The value, which will be converted to a String.
     */
    public void set(String name, double value) {
        paramMap.put(name, "" + value);
    }

    /**
     * Same as {@link #get(String)}
     */
    public String getParameter(String name) {
        return paramMap.get(name);
    }

    /**
     * Gets the value based on the name key.
     * @param name The name or key to be retrieved.
     * @return String value or null if the value was never set.
     */
    public String get(String name) {
        return paramMap.get(name);
    }

    /**
     * Gets the value based on the name key.
     * @param name The name or key to be retrieved.
     * @return the value or 0 if the value was never set.
     * @throws NumberFormatException if the value isn't a valid number
     */
    public int getInt(String name) {
        String value = paramMap.get(name);
        if (value != null) {
            return new Integer(value);
        }
        return 0;
    }

    /**
     * Gets the value based on the name key.
     * @param name The name or key to be retrieved.
     * @return the value or 0 if the value was never set.
     * @throws NumberFormatException if the value isn't a valid number
     */
    public long getLong(String name) {
        String value = paramMap.get(name);
        if (value != null) {
            return new Long(value);
        }
        return 0;
    }

    /**
     * Gets the value based on the name key.
     * @param name The name or key to be retrieved.
     * @return the value or 0 if the value was never set.
     * @throws NumberFormatException if the value isn't a valid number
     */
    public float getFloat(String name) {
        String value = paramMap.get(name);
        if (value != null) {
            return new Float(value);
        }
        return 0;
    }

    /**
     * Gets the value based on the name key.
     * @param name The name or key to be retrieved.
     * @return the value or 0 if the value was never set.
     * @throws NumberFormatException if the value isn't a valid number
     */
    public double getDouble(String name) {
        String value = paramMap.get(name);
        if (value != null) {
            return new Double(value);
        }
        return 0;
    }

    /**
     * Gets the value based on the name key or throws an exception if the value wasn't set.
     * @param name The name or key to be retrieved.
     * @return the string value.
     * @throws IllegalStateException if the value wasn't set
     */
    public String getRequiredParameter(String name) {
        String param = getParameter(name);
        if (param == null) {
            throw new IllegalStateException("RequiredParam doesn't exist!");
        }
        return param;
    }

    /**
     * Adds the parameters for the specified keys.
     *
     * @param fromPlaceParameters The parameters object to extract from.
     * @param setKeys The list of keys that should be retrieved.
     */
    public void addPlaceParameters(PlaceParameters fromPlaceParameters, List<String> setKeys) {
        if (setKeys != null) {
            for (String key: setKeys) {
                String existingValue = paramMap.get(key);
                String fromValue = fromPlaceParameters.paramMap.get(key);
                if (existingValue == null) {
                    paramMap.put(key, fromValue);
                } else if (!existingValue.equals(fromValue)) {
                    log.warning(key + " has second value that is being ignored: " + fromValue);
                }
            }
        }
    }

    /**
     * Creates an encoded url string with each key/value pair with an '=' and starting with and '&amp;' and '?'
     * between each key/value.
     *
     * @return Example: "&amp;key1=foo&amp;key2=Some+sentence+that+was+encoded."
     */
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
