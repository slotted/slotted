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

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.user.client.History;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * HistoryMapper is an abstract base class that manages generation and parsing of History Tokens.
 * This class is extended by every implementation of Slotted and defines which SlottedPlaces need
 * URL management.
 */
abstract public class HistoryMapper {

    class DefaultPlaceTokenizer implements PlaceTokenizer<SlottedPlace> {
        private Class<? extends SlottedPlace> placeClass;

        DefaultPlaceTokenizer(Class<? extends SlottedPlace> placeClass) {
            this.placeClass = placeClass;
        }

        @Override public SlottedPlace getPlace(String token) {
            return placeFactory.newInstance(placeClass);
        }

        @Override public String getToken(SlottedPlace place) {
            return "";
        }
    }

    private static final Logger log = Logger.getLogger(HistoryMapper.class.getName());
    private PlaceFactory placeFactory = GWT.create(PlaceFactory.class);
    private HashMap<String, PlaceTokenizer<? extends SlottedPlace>> nameToTokenizerMap = new HashMap<String, PlaceTokenizer<? extends SlottedPlace>>();
    private HashMap<Class, String> placeToNameMap = new HashMap<Class, String>();
    private SlottedPlace defaultPlace;
    private SlottedPlace errorPlace;
    private SlottedController controller;
    private PlaceHistoryMapper legacyHistoryMapper;
    private boolean handlingHistory;

    /**
     * Default constructor which adds itself as a History listener and calls init() on the base
     * class to register all SlottedPlaces.
     */
    public HistoryMapper() {
        init();
    }

    /**
     * Called by the Constructor to register all the SlottedPlaces that will be managed.
     *
     * @see #registerDefaultPlace(SlottedPlace)
     * @see #registerPlace(SlottedPlace)
     */
    protected abstract void init();

    /**
     * Called by SlottedController to provide the circular reference.
     */
    protected void setController(SlottedController controller) {
        this.controller = controller;
    }

    /**
     * Called by SlottedController to provide legacy HistoryMapper to process Places not migrated
     * to Slotted framework.
     */
    protected void setLegacyHistoryMapper(PlaceHistoryMapper legacyHistoryMapper) {
        this.legacyHistoryMapper = legacyHistoryMapper;
    }

    /**
     * Returns the SlottedPlace instance that represents the place to navigate to if the history
     * token is empty.
     */
    public SlottedPlace getDefaultPlace() {
        return defaultPlace;
    }

    /**
     * @see SlottedController#setDefaultPlace(SlottedPlace)
     */
    public void setDefaultPlace(SlottedPlace place) {
        if (defaultPlace != null) {
            throw new IllegalStateException("Default place already set.");
        }
        defaultPlace = place;
        if (errorPlace == null) {
            errorPlace = defaultPlace;
        }
    }

    /**
     * @see SlottedController#setErrorPlace(SlottedPlace)
     */
    public void setErrorPlace(SlottedPlace place) {
        if (errorPlace == null || errorPlace == defaultPlace) {
            errorPlace = place;
        } else {
            throw new IllegalStateException("Error place already set.");
        }
    }

    /**
     * @deprecated
     * This was broken into 2 calls.
     *
     * @see #setDefaultPlace(SlottedPlace)
     * @see #registerPlace(Class)
     */
    public void registerDefaultPlace(SlottedPlace place) {
        setDefaultPlace(place);
        registerPlace(place.getClass());
    }

    /**
     * @deprecated
     * This was broken into 2 calls.
     *
     * @see #setDefaultPlace(SlottedPlace)
     * @see #registerPlace(Class, String)
     */
    public void registerDefaultPlace(SlottedPlace place, String name) {
        setDefaultPlace(place);
        registerPlace(place.getClass(), name);
    }

    /**
     * @deprecated
     *
     * This method is replaced by {@link #registerPlace(Class)}
     */
    public void registerPlace(SlottedPlace place) {
        registerPlace(place.getClass());
    }

    /**
     * Registers the passed SlottedPlace be handled in generating and parsing History tokens.  The
     * SlottedPlace's simple class name will be used in the HistoryToken.  If the name ends with
     * "Place", that will be stripped off.  (i.e. "HomePlace" will appear as "Home" in the token)
     *
     * @param placeClass The Class of the place to be managed.  The Place must have a default
     *                   constructor, but it may be private.
     * @see #registerPlace(SlottedPlace, String)
     */
    public void registerPlace(Class<? extends SlottedPlace> placeClass) {
        registerPlace(placeClass, null, null);
    }

    /**
     * Same as {@link #registerPlace(SlottedPlace)}, but allows for the Place to create token
     * with parameters specific to the Place.  This allows parameters to be Place specific to
     * prevent name collisions.
     *
     * @param placeClass The Class of the place to be managed.  The Place must have a default
     *                   constructor, but it may be private.
     * @param tokenizer That handles the creation of the token and parsing the token into a Place.
     */
    public void registerPlace(Class<? extends SlottedPlace> placeClass,
            PlaceTokenizer<? extends SlottedPlace> tokenizer)
    {
        registerPlace(placeClass, null, tokenizer);
    }

    /**
     * @deprecated
     * This method is replaced by {@link #registerPlace(Class, String)}
     */
    public void registerPlace(SlottedPlace place, String name) {
        registerPlace(place.getClass(), name);
    }

    /**
     * Same as {@link #registerPlace(SlottedPlace)}, but allows Places URL token to be
     * overridden, instead of using the simple class name.
     *
     * @param placeClass The Class of the place to be managed.  The Place must have a default
     *                   constructor, but it may be private.
     * @param name The new URL token to display in the History token, must be URL safe.
     */
    public void registerPlace(Class<? extends SlottedPlace> placeClass, String name) {
        registerPlace(placeClass, name, null);
    }

    /**
     * Same as {@link #registerPlace(SlottedPlace)}, but allows for overridden name, and
     * parameter tokens.
     *
     * @param placeClass The Class of the place to be managed.  The Place must have a default
     *                   constructor, but it may be private.
     * @param name The new URL token to display in the History token, must be URL safe.
     * @param tokenizer That handles the creation of the token and parsing the token into a Place.
     *
     * @see #registerPlace(Class, PlaceTokenizer)
     * @see #registerPlace(Class, String)
     */
    public void registerPlace(Class<? extends SlottedPlace> placeClass, String name,
            PlaceTokenizer<? extends SlottedPlace> tokenizer)
    {
        SlottedPlace place = placeFactory.newInstance(placeClass);
        if (place == null) {
            throw new IllegalStateException("To register a Place, it must have a default " +
                    "constructor that may be private: " + placeClass.getName());
        }
        Slot[] childSlots = place.getChildSlots();
        if (childSlots != null) {
            for (Slot child: childSlots) {
                if (child.getOwnerPlace() == null || child.getDefaultPlace() == null) {
                    throw new IllegalStateException(place + " has a Slot that is defined incorrectly. " +
                            "Slots must define a ParentPlace and DefaultPlace.");
                }
                SlottedPlace defaultPlace = child.getDefaultPlace();
                if (!defaultPlace.getParentSlot().equals(child)) {
                    throw new IllegalStateException(place + " has a Slot with a default place " +
                            "for different slot: " + defaultPlace);
                }
            }
        }

        if (tokenizer == null) {
            tokenizer = new DefaultPlaceTokenizer(placeClass);
        }

        if (name == null) {
            name = placeClass.getName();
            int index = name.lastIndexOf(".");
            if (name.endsWith("Place")) {
                name = name.substring(index + 1, name.length() - 5);
            } else {
                name = name.substring(index + 1);
            }
        }

        nameToTokenizerMap.put(name, tokenizer);
        placeToNameMap.put(placeClass, name);
    }

    /**
     * Called by SlottedController to provide a URL empty history token when navigating to the
     * default place.
     */
    protected void goToDefaultPlace() {
        History.newItem("", true);
    }

    /**
     * Called by the History listener to parse and navigate the new history token.
     *
     * @param token History Token that needs to be navigated to.
     */
    protected void handleHistory(String token) {
        RuntimeException parsingException = null;
        handlingHistory = true;
        try {
            if (token == null || token.trim().isEmpty()) {
                navDefaultPlace();
            } else {
                SlottedPlace[] places = null;
                try {
                    PlaceParameters parameters = new PlaceParameters();

                    String[] split = token.split("\\?");
                    String[] placeTokens = split[0].split("/");

                    if (split.length > 1) {
                        String[] paramPairs = split[1].split("&");

                        for (String pair: paramPairs) {
                            String[] pairSplit = pair.split("=");
                            parameters.setParameter(pairSplit[0], pairSplit[1]);
                        }
                    }

                    places = new SlottedPlace[placeTokens.length];
                    for (int i = 0; i < places.length; i++) {
                        String[] placeParts = placeTokens[i].split(":", 2);
                        String parameterToken = "";
                        if (placeParts.length == 2) {
                            parameterToken = placeParts[1];
                        }

                        PlaceTokenizer<? extends SlottedPlace> tokenizer = nameToTokenizerMap.get(placeParts[0]);
                        places[i] = tokenizer.getPlace(parameterToken);
                        if (places[i] == null) {
                            throw new IllegalStateException("Place not defined:" + placeTokens[i]);
                        }
                        places[i].setPlaceParameters(parameters);
                    }
                } catch (RuntimeException e) {
                    parsingException = e;
                }

                if (parsingException == null) {
                    controller.goTo(places[0], places);
                } else {
                    if (legacyHistoryMapper != null) {
                        Place place = legacyHistoryMapper.getPlace(token);
                        if (place != null) {
                            parsingException = null;
                            controller.goTo(place);
                        }
                    }
                }

                if (parsingException != null) {
                    log.log(Level.SEVERE, "Error parsing url", parsingException);
                    navDefaultPlace();
                }
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error processing goTo", e);
            navErrorPlace();
        }
        handlingHistory = false;
    }

    private void navDefaultPlace() {
        if (defaultPlace == null) {
            throw new IllegalStateException("No default place defined.  Make sure that " +
                    "setDefaultPlace() is called");
        }
        controller.goTo(defaultPlace);
    }

    private void navErrorPlace() {
        if (errorPlace == null) {
            throw new IllegalStateException("No error or default place defined.  Make sure that " +
                    "setErrorPlace() or setDefaultPlace() is called");
        }
        controller.goTo(errorPlace);
    }

    public void extractParameters(SlottedPlace place, PlaceParameters intoPlaceParameters) {
        place.extractParameters(intoPlaceParameters);

        String name = placeToNameMap.get(place.getClass());
        PlaceTokenizer tokenizer = nameToTokenizerMap.get(name);
        if (tokenizer instanceof SlottedTokenizer) {
            //noinspection unchecked
            ((SlottedTokenizer) tokenizer).extractParameters(intoPlaceParameters, place);
        }
    }

    public PlaceParameters extractParameters(ArrayList<SlottedPlace> completeNonDefaults) {
        PlaceParameters placeParameters = new PlaceParameters();

        for (SlottedPlace place: completeNonDefaults) {
            extractParameters(place, placeParameters);
        }

        return placeParameters;
    }

    /**
     * Creates a History token for the passed place, which can be used in links or other navigation
     * URLs.
     *
     * @param place The SlottedPlace that will be navigated to.
     * @param parameters The parameters that should be added to the token.
     * @return History token that can be added to a base URL for navigation.
     */
    public String createToken(SlottedPlace place, PlaceParameters parameters) {
        String token = placeToNameMap.get(place.getClass());

        if (parameters != null) {
            token += parameters.toString();
        }

        return token;
    }

    /**
     * Generates the History token for the currently display Places.
     */
    public void createToken() {
        if (!handlingHistory) {
            String token = createToken(controller.getRoot());

            History.newItem(token, false);
        }
    }

    protected String createToken(ActiveSlot activeSlot) {
        String token;
        if (activeSlot.getPlace() instanceof WrappedPlace) {
            Place place = ((WrappedPlace) activeSlot.getPlace()).getPlace();
            token = legacyHistoryMapper.getToken(place);

        } else {
            token = createPageList(activeSlot);

            PlaceParameters parameters = controller.getCurrentParameters();
            if (parameters != null) {
                token += parameters.toString();
            }
        }

        return token;
    }

    private String createPageList(ActiveSlot activeSlot) {
        SlottedPlace place = activeSlot.getPlace();
        String name = placeToNameMap.get(place.getClass());
        if (name == null) {
            throw new IllegalStateException("Place not registered:" + activeSlot.getPlace().getClass().getName());
        }
        PlaceTokenizer tokenizer = nameToTokenizerMap.get(name);
        @SuppressWarnings("unchecked")
        String params = tokenizer.getToken(place);

        String token = name;
        if (params != null && !params.isEmpty()) {
            token += ":" + params;
        }

        for (ActiveSlot child: activeSlot.getChildren()) {
            token += "/" + createPageList(child);
        }
        return token;
    }

}
