package com.googlecode.slotted.client;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.gwt.activity.shared.Activity;

/**
 * Defines the Activity that should be constructed for a place by the {@link CodeSplitMapper}.
 *
 * @see CodeSplit
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PlaceActivity {
    Class<? extends Activity> value();
}
