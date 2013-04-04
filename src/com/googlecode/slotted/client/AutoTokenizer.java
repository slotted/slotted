package com.googlecode.slotted.client;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * todo javadoc
 * @param <P>
 */
public interface AutoTokenizer<P extends Place> extends PlaceTokenizer<P> {
    void extractFields(PlaceParameters intoPlaceParameters, P place);
    void fillFields(PlaceParameters placeParameters, P place);
}
