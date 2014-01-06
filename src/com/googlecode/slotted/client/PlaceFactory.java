package com.googlecode.slotted.client;

import com.google.gwt.place.shared.Place;

/**
 * PlaceFactory is a generator class, which provides ability to create a Place or SlottedPlace
 * form a Class instance.  This is used by the HistoryMapper to create Places based on the history token.
 */
public interface PlaceFactory {
    Place newInstance(Class placeClass);
}