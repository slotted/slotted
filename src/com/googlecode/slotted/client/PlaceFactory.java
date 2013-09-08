package com.googlecode.slotted.client;

import com.google.gwt.place.shared.Place;

public interface PlaceFactory {
    Place newInstance(Class placeClass);
}