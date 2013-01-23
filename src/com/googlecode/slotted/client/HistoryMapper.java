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

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.History;

import java.util.HashMap;

/**
 *
 */
abstract public class HistoryMapper {

    private HashMap<String, SlottedPlace> nameToPlaceMap = new HashMap<String, SlottedPlace>();
    private HashMap<Class, String> placeToNameMap = new HashMap<Class, String>();
    private SlottedPlace defaultPlace;
    private SlottedController controller;
    private PlaceHistoryMapper legacyHistoryMapper;
    private boolean handlingHistory;

    public HistoryMapper() {
        History.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override public void onValueChange(ValueChangeEvent<String> event) {
                handleHistory(event.getValue());
            }
        });

        init();
        if (defaultPlace == null) {
            System.out.println("WARNING: Default place not set.");
        }
    }

    /**
     *
     */
    protected abstract void init();

    /**
     *
     * @param controller
     */
    public void setController(SlottedController controller) {
        this.controller = controller;
    }

    public void setLegacyHistoryMapper(PlaceHistoryMapper legacyHistoryMapper) {
        this.legacyHistoryMapper = legacyHistoryMapper;
    }

    public SlottedPlace getDefaultPlace() {
        return defaultPlace;
    }

    public void registerDefaultPlace(SlottedPlace place) {
        if (defaultPlace != null) {
            throw new IllegalStateException("Default place already set.");
        }
        defaultPlace = place;
        registerPlace(place);
    }

    public void registerDefaultPlace(SlottedPlace place, String name) {
        if (defaultPlace != null) {
            throw new IllegalStateException("Default place already set.");
        }
        defaultPlace = place;
        registerPlace(place, name);
    }

    public void registerPlace(SlottedPlace place) {
        String name = place.getClass().getName();
        int index = name.lastIndexOf(".");
        if (name.endsWith("Place")) {
            name = name.substring(index + 1, name.length() - 5);
        } else {
            name = name.substring(index + 1);
        }

        registerPlace(place, name);
    }

    public void registerPlace(SlottedPlace place, String name) {
        nameToPlaceMap.put(name, place);
        placeToNameMap.put(place.getClass(), name);
    }

    public void goToDefaultPlace() {
        History.newItem("", true);
    }

    public void handleHistory(String token) {
        RuntimeException parsingException = null;
        handlingHistory = true;
        if (token == null || token.trim().isEmpty()) {
            if (defaultPlace == null) {
                throw new IllegalStateException("No default place defined.  Make sure that " +
                        "registerDefaultPlace() is called");
            }
            controller.goTo(defaultPlace);
        } else {
            try {
                PlaceParameters parameters = new PlaceParameters();

                String[] split = token.split("\\?");
                String[] placeNames = split[0].split("/");

                if (split.length > 1) {
                    String[] paramPairs = split[1].split("&");

                    for (String pair: paramPairs) {
                        String[] pairSplit = pair.split("=");
                        parameters.setParameter(pairSplit[0], pairSplit[1]);
                    }
                }

                SlottedPlace[] places = new SlottedPlace[placeNames.length];
                for (int i = 0; i < places.length; i++) {
                    places[i] = nameToPlaceMap.get(placeNames[i]);
                    if (places[i] == null) {
                        throw new IllegalStateException("Place not defined:" + placeNames[i]);
                    }
                    places[i].retrieveParameters(parameters);
                }

                controller.goTo(places[0], places);
            } catch (RuntimeException e) {
                parsingException = e;
            }
        }

        if (parsingException != null) {
            if (legacyHistoryMapper != null) {
                Place place = legacyHistoryMapper.getPlace(token);
                controller.goTo(place);
            } else {
                throw parsingException;
            }
        }

        handlingHistory = false;
    }

    //todo javadoc
    public String createToken(SlottedPlace place, PlaceParameters parameters) {
        String token = placeToNameMap.get(place.getClass());

        if (parameters != null) {
            token += parameters.toString();
        }

        return token;
    }

    public void createToken() {
        if (!handlingHistory) {
            String token = createToken(controller.getRoot());

            History.newItem(token, false);
        }
    }

    public String createToken(ActiveSlot activeSlot) {
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
        String token = placeToNameMap.get(activeSlot.getPlace().getClass());
        if (token == null) {
            throw new IllegalStateException("Place not registered:" + activeSlot.getPlace().getClass().getName());
        }

        for (ActiveSlot child: activeSlot.getChildren()) {
            token += "/" + createPageList(child);
        }
        return token;
    }

}
