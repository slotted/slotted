package com.googlecode.slotted.client;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

import java.util.HashMap;

/**
 * todo javadoc
 * @param <P>
 */
public interface AutoTokenizer<P extends Place> extends PlaceTokenizer<P> {
    public HashMap<Class, AutoTokenizer> tokenizers = new HashMap<Class, AutoTokenizer>();

    void extractFields(PlaceParameters intoPlaceParameters, P place);
    void fillFields(PlaceParameters placeParameters, P place);
    boolean equals(P p1, P p2);
}
