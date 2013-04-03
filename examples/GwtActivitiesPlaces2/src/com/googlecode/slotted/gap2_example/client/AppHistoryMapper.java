package com.googlecode.slotted.gap2_example.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.googlecode.slotted.client.HistoryMapper;

public class AppHistoryMapper extends HistoryMapper {
    @Override protected void init() {
        setDefaultPlace(new HelloPlace("World!"));
        registerPlace(HelloPlace.class, new HelloPlace.Tokenizer());
        registerPlace(GoodbyePlace.class, "gb", new GoodbyePlace.Tokenizer());
        registerPlace(BasePlace.class, (PlaceTokenizer) GWT.create(BasePlace.Tokenizer.class));
    }
}
