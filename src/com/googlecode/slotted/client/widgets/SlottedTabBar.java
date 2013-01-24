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
package com.googlecode.slotted.client.widgets;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.TabBar;
import com.googlecode.slotted.client.NewPlaceEvent;
import com.googlecode.slotted.client.SlottedController;
import com.googlecode.slotted.client.SlottedPlace;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * SlottedTabBar, in conjunction with a Slot, creates the functionality of a TabPanel that uses
 * Activities as its contents.
 *
 * To use: create a normal Slotted hierarchy with all the Tab contents as Activities/Places with the
 * same destination Slot.  The call {@link #addTab(SlottedPlace, String)} for each tab, and give it
 * a label to display on the tab.  Slotted will now take care of the rest.
 */
public class SlottedTabBar extends TabBar implements SelectionHandler<Integer>, NewPlaceEvent.Handler {
    private ArrayList<SlottedPlace> places = new ArrayList<SlottedPlace>(5);
    private SlottedController slottedController;

    /**
     * Constructs SlottedTabBar widget empty of tabs, but ready to navigate.
     *
     * @param slottedController The SlottedController used for the site.  Needed for navigation.
     */
    public SlottedTabBar(SlottedController slottedController) {
        this.slottedController = slottedController;
        slottedController.getEventBus().addHandler(NewPlaceEvent.Type, this);
        addSelectionHandler(this);
    }

    /**
     * Adds a new Tab to the bar.
     *
     * @param place The Place to navigate to when tab is selected.
     * @param label The label that will be displayed on the tab.
     */
    public void addTab(SlottedPlace place, String label) {
        this.addTab(label);
        places.add(place);
    }

    /**
     * Called by TabBar widget when a tab is selected.
     */
    @Override
    public void onSelection(SelectionEvent<Integer> event) {
        int index = event.getSelectedItem();
        SlottedPlace place = places.get(index);
        slottedController.goTo(place);
    }

    /**
     * Called by EventBus when ever there is any navigation performed by the SlottedController.
     *
     * @param newPlaces List of SlottedPlaces that are being navigated to.
     */
    @Override
    public void newPlaces(LinkedList<SlottedPlace> newPlaces) {
        for (int i = 0; i < places.size(); i++) {
            SlottedPlace tabPlace = places.get(i);
            if (newPlaces.contains(tabPlace)) {
                selectTab(i, false);
                break;
            }
        }
    }
}
