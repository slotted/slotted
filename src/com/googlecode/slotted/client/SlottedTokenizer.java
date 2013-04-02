package com.googlecode.slotted.client;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * todo javadoc
 * @param <P>
 */
public interface SlottedTokenizer<P extends Place> extends PlaceTokenizer<P> {
    void extractParameters(PlaceParameters intoPlaceParameters, P place);
}
