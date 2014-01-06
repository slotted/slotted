package com.googlecode.slotted.client;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

import java.util.HashMap;

/**
 * AutoTokenizer is a generator class, uses the @TokenizerParameter and @GlobalParameter to create code for making
 * the history token and parsing a history token into a new Place.
 *
 * More information on AutoTokenizer and AutoHistoryMapper can be found on the wiki here:
 * https://code.google.com/p/slotted/wiki/AutoTokenizerAutoHistoryMapper
 *
 * @param <P> The Place type that will be created by the Tokenizer.  This is required to correctly
 *           generate the code for the AutoTokenizer.
 */
public interface AutoTokenizer<P extends Place> extends PlaceTokenizer<P> {
    public HashMap<Class, AutoTokenizer> tokenizers = new HashMap<Class, AutoTokenizer>();

    void extractFields(PlaceParameters intoPlaceParameters, P place);
    void fillFields(PlaceParameters placeParameters, P place);
    boolean equals(P p1, P p2);
}
