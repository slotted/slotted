package com.googlecode.slotted.gap_example.client;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class GoodbyePlace extends Place {
    private String helloName;

    public GoodbyePlace(String token) {
        this.helloName = token;
    }

    public String getHelloName() {
        return helloName;
    }

    public static class Tokenizer implements PlaceTokenizer<GoodbyePlace> {
        @Override
        public String getToken(GoodbyePlace place) {
            return place.getHelloName();
        }

        @Override
        public GoodbyePlace getPlace(String token) {
            return new GoodbyePlace(token);
        }
    }
}