package com.googlecode.slotted.client;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines the Ginjector to use in the {@link CodeSplitMapper}.
 *
 * @see CodeSplitMapperClass
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CodeSplitGinjector {
    Class value();
}
