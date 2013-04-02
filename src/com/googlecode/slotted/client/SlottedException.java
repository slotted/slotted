package com.googlecode.slotted.client;

/**
 * todo javadoc
 */
public class SlottedException extends RuntimeException {
    private SlottedException() {
    }

    public SlottedException(String s) {
        super(s);
    }

    private SlottedException(Throwable e) {
        super(e);
    }

    public static SlottedException wrap(Throwable e) {
        if (e instanceof SlottedException) {
            return (SlottedException) e;
        } else {
            return new SlottedException(e);
        }
    }
}
