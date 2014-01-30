package com.googlecode.slotted.client;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Provides a list of child Activities that should be cached as long as the Activity with the annotation
 * exists.  The child Activities are created when they are navigated to, but when navigated away from,
 * instead of calling mayStop() and onStop(),  {@link SlottedActivity#mayBackground()} and
 * {@link SlottedActivity#onBackground()} are called.
 *
 * When navigating away from the Activity with the annotation, mayStop() and onStop() are called as normal,
 * but it is also mayStop() and onStop() will be called on all backgrounded Activities.  This means that
 * backgrounded Activities may stop navigation even thought they aren't displayed.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheActivities {
    Class<? extends SlottedPlace>[] value();
}
