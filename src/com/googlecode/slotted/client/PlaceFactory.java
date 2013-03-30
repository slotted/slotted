package com.googlecode.slotted.client;

public interface PlaceFactory {
    SlottedPlace newInstance(Class placeClass);
}