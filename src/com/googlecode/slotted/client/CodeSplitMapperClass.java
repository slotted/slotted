package com.googlecode.slotted.client;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines the CodeSplitMapperClass a Place should use to construct the Activity.
 * The {@link CodeSplitMapper} needs to be registered with SlottedController.
 *
 * @see PlaceActivity
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CodeSplitMapperClass {
    Class<? extends CodeSplitMapper> value();
}
