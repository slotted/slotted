package com.googlecode.slotted.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.place.shared.Place;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.ResettableEventBus;
import com.googlecode.slotted.client.NewPlacesEvent;
import com.googlecode.slotted.client.SlottedController;
import com.googlecode.slotted.client.SlottedPlace;

import java.util.HashMap;

abstract public class SlottedNavWidgetHelper<D extends HasClickHandlers> implements NewPlacesEvent.Handler {

    protected HashMap<Class, D> byClassMap = new HashMap<Class, D>();
    protected HashMap<Place, D> strictEqualMap = new HashMap<Place, D>();
    protected HashMap<D, Place> widgetMap = new HashMap<D, Place>();
    private boolean clearActiveOnNoMatch = true;

    private SlottedController slottedController;

    public SlottedNavWidgetHelper(SlottedController slottedController, EventBus eventBus) {
        assert eventBus instanceof ResettableEventBus : "Must be a resettable EventBus to prevent leaks";

        this.slottedController = slottedController;
        eventBus.addHandler(NewPlacesEvent.Type, this);
    }

    abstract protected void handlePlaceActive(D widget, Place place, boolean active);

    public boolean isClearActiveOnNoMatch() {
        return clearActiveOnNoMatch;
    }

    public void setClearActiveOnNoMatch(boolean clearActiveOnNoMatch) {
        this.clearActiveOnNoMatch = clearActiveOnNoMatch;
    }

    public void addNavWidget(D widget, SlottedPlace place) {
        addNavWidget(widget, place, false);
    }

    public void addNavWidget(D widget, SlottedPlace place, boolean strictEquals) {
        addNavWidget(widget, place, strictEquals, false);
    }

    public void addNavWidget(D widget, SlottedPlace place, boolean strictEquals, boolean alternative) {
        if (!alternative) {
            addHandler(widget, place);
        }

        widgetMap.put(widget, place);
        if (strictEquals) {
            strictEqualMap.put(place, widget);
        } else {
            byClassMap.put(place.getClass(), widget);
        }

        SlottedPlace current = SlottedController.instance.getCurrentPlace(place.getClass());
        if (strictEquals && place.equals(current)) {
            clearActive();
            handlePlaceActive(widget, place, true);

        } else if (!strictEquals && current != null && place.getClass().equals(current.getClass())) {
            clearActive();
            handlePlaceActive(widget, place, true);
        }
    }

    protected void addHandler(D widget, final SlottedPlace place) {
        widget.addClickHandler(new ClickHandler() {
            @Override public void onClick(ClickEvent event) {
                slottedController.goTo(place);
            }
        });
    }

    public void clearActive() {
        for (D widget: strictEqualMap.values()) {
            handlePlaceActive(widget, null, false);
        }
        for (D widget: byClassMap.values()) {
            handlePlaceActive(widget, null, false);
        }
    }

    public void newPlaces(NewPlacesEvent event) {
        if (event.getSource() == slottedController) {
            D widget = null;
            SlottedPlace activePlace = null;

            for (SlottedPlace place : event.getNewPlaces()) {
                widget = strictEqualMap.get(place);
                if (widget != null) {
                    activePlace = place;
                    break;
                }
            }

            if (widget == null) {
                for (SlottedPlace place : event.getNewPlaces()) {
                    widget = byClassMap.get(place.getClass());
                    if (widget != null) {
                        activePlace = place;
                        break;
                    }
                }
            }

            if (widget != null) {
                clearActive();
                handlePlaceActive(widget, activePlace, true);
            } else if (clearActiveOnNoMatch) {
                clearActive();
            }
        }
    }
}
