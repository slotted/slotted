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
package org.npc.slotted.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;

import java.util.HashMap;

/**
 *
 */
abstract public class HistoryMapper {

    private HashMap<String, SlottedPlace> nameToPlaceMap = new HashMap<String, SlottedPlace>();
    private HashMap<Class, String> placeToNameMap = new HashMap<Class, String>();
    private SlottedController controller;
    private boolean handlingHistory;

    public HistoryMapper() {
        History.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override public void onValueChange(ValueChangeEvent<String> event) {
                handleHistory(event.getValue());
            }
        });

        init();
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

    public void registerPlace(SlottedPlace place) {
        String name = place.getClass().getName();
        int index = name.lastIndexOf(".");
        name = name.substring(index + 1);

        registerPlace(place, name);
    }

    public void registerPlace(SlottedPlace place, String name) {
        nameToPlaceMap.put(name, place);
        placeToNameMap.put(place.getClass(), name);
    }

    public void handleHistory(String token) {
        handlingHistory = true;
        if (token == null || token.trim().isEmpty()) {
            controller.goToDefaultPlace();
        } else {
            PlaceParameters parameters = new PlaceParameters();

            String[] split = token.split("\\?");
            String[] placeNames = split[0].split("/");

            SlottedPlace[] places = new SlottedPlace[placeNames.length];
            for (int i = 0; i < places.length; i++) {
                places[i] = nameToPlaceMap.get(placeNames[i]);
                if (places[i] instanceof ParamPlace) {
                    ((ParamPlace)places[i]).setPlaceParameters(parameters);
                }
            }

            if (split.length > 1) {
                String[] paramPairs = split[1].split("&");

                for (String pair: paramPairs) {
                    String[] pairSplit = pair.split("=");
                    parameters.setParameter(pairSplit[0], pairSplit[1]);
                }
            }

            controller.goTo(places[0], parameters, places);
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
        String token = createPageList(activeSlot);

        PlaceParameters parameters = controller.getCurrentParameters();
        if (parameters != null) {
            token += parameters.toString();
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
