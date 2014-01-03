package com.googlecode.slotted.client;

/**
 * A wrapper for exceptions that happen while the SlottedController is processing.
 * This is similar to the GWT UmbrellaExceptions, which is necessary to handle non Runtime Exceptions.
 */
public class SlottedException extends RuntimeException {
    protected SlottedException() {
    }

    /**
     * Creates a exception with a specific message.
     */
    public SlottedException(String s) {
        super(s);
    }

    /**
     * Creates an exception with a root cause.
     * @param e
     */
    protected SlottedException(Throwable e) {
        super(e);
    }

    /**
     * Wraps exceptions to make all exceptions RuntimeExceptions.
     *
     * @param e The exception to wrap.
     * @return SlottedException that is ready to be thrown.
     */
    public static SlottedException wrap(Throwable e) {
        if (e instanceof SlottedException) {
            return (SlottedException) e;
        } else {
            return new SlottedException(e);
        }
    }
}
