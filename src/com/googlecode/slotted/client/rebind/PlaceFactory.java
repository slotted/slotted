package com.googlecode.slotted.client.rebind;

import com.googlecode.slotted.client.SlottedPlace;

public interface PlaceFactory {
    SlottedPlace newInstance(Class placeClass);
}