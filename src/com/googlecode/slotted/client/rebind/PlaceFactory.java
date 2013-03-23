package com.googlecode.slotted.client.rebind;

import com.google.gwt.place.shared.Place;

public interface PlaceFactory {
    Place newInstance(String className);
}