package com.googlecode.slotted.gin_codesplitting.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.AsyncProvider;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;
import com.googlecode.slotted.client.ProviderPlace;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.TokenizerUtil;

public class GoodbyePlace extends ProviderPlace {
    private String helloName;

    private GoodbyePlace() {
    }

    public GoodbyePlace(String token) {
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

    @Override public AsyncProvider<Activity, Throwable> getActivityProvider() {
        return new GapSplitGroup();
    }

    @Prefix("gb")
    public static class Tokenizer implements PlaceTokenizer<GoodbyePlace> {
        @Override
        public String getToken(GoodbyePlace place) {
            return TokenizerUtil.build()
                    .add(place.getHelloName())
                    .tokenize();
        }

        @Override
        public GoodbyePlace getPlace(String token) {
            TokenizerUtil util = TokenizerUtil.extract(token);
            return new GoodbyePlace(util.get());
        }
    }
}
