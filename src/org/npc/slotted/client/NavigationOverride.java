package org.npc.slotted.client;

import java.util.ArrayList;

public interface NavigationOverride {
    /**
     * Called for every place request.  If override is desired return new list of places, otherwise
     * return the passed places.
     *
     * @param requestedPlaces List of places with the first item being the requested place, and the
     *                        other are non default places.
     * @return The places that should be navigated to.  Null is not allowed.
     */
    ArrayList<SlottedPlace> checkOverrides(ArrayList<SlottedPlace> requestedPlaces);
}
