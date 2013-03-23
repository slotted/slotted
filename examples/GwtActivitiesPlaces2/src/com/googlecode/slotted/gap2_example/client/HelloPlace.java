package com.googlecode.slotted.gap2_example.client;

import com.google.gwt.place.shared.PlaceTokenizer;
import com.googlecode.slotted.client.MappedSlottedPlace;
import com.googlecode.slotted.client.Slot;

public class HelloPlace extends MappedSlottedPlace {
    private String helloName;

    private HelloPlace() {
    }

    public HelloPlace(String token) {
        this.helloName = token;
    }

    public String getHelloName() {
        return helloName;
    }

    @Override public Slot getParentSlot() {
        return BasePlace.SLOT;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }

    public static class Tokenizer implements PlaceTokenizer<HelloPlace> {
        @Override
        public String getToken(HelloPlace place) {
            return place.getHelloName();
        }

        @Override
        public HelloPlace getPlace(String token) {
            return new HelloPlace(token);
        }
    }
}
