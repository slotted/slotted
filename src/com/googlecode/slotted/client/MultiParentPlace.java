package com.googlecode.slotted.client;

import java.util.List;

abstract public class MultiParentPlace extends ContainerPlace {
    @TokenizerParameter
    protected int slotIndex = -1;

    protected MultiParentPlace() {
    }

    protected MultiParentPlace(Slot parentSlot) {
        setParentSlotIndex(parentSlot);
    }

    protected void setParentSlotIndex(Slot parentSlot) {
        Slot[] parentSlots = getParentSlots();
        for (int i = 0; i < parentSlots.length; i++) {
            if (parentSlot.equals(parentSlots[i])) {
                slotIndex = i;
            }
        }
        assert slotIndex != -1 : "Passed slot is not a valid Parent as defined by getParentSlots()";
    }

    public void indexParentPlace(List<SlottedPlace> possibleParentPlaces, boolean force) {
        if (force || slotIndex == -1) {
            Slot[] parentSlots = getParentSlots();
            for (SlottedPlace possiblePlace: possibleParentPlaces) {
                if (!(possiblePlace instanceof MultiParentPlace)) {
                    Slot[] possibleSlots = possiblePlace.getChildSlots();
                    if (possibleSlots != null) {
                        for (Slot possibleSlot: possibleSlots) {
                            for (int i = 0; i < parentSlots.length; i++) {
                                if (possibleSlot.equals(parentSlots[i])) {
                                    slotIndex = i;
                                    return;
                                }
                            }
                        }
                    }
                }
            }
            slotIndex = 0;
        }
    }

    public abstract Slot[] getParentSlots();

    @Override public final Slot getParentSlot() {
        Slot[] parentSlots = getParentSlots();
        assert slotIndex != -1;
        return parentSlots[slotIndex];
    }
}
