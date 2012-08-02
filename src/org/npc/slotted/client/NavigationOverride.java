package org.npc.slotted.client;

import java.util.ArrayList;

public interface NavigationOverride {
    ArrayList<SlottedPlace> checkNavigation(ArrayList<SlottedPlace> requestedPlaces);
}
