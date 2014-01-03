package com.googlecode.slotted.client;

/**
 * A special SlottedException that happens during the navigation initialization process.  This exception also
 * contains information about the activityToken that caused the problem which can be hard to determine once
 * client code is obfuscated.
 *
 * These exceptions will most likely cause bad UI displays.
 */
public class SlottedInitException extends SlottedException {

    private String goToList;
    private String activityToken;

    private SlottedInitException() {}

    /**
     * Creates the exception with information place that caused the problem.
     *
     * @param activityToken The history token that is obfuscated.
     * @param cause The exception that caused the problem.
     */
    public SlottedInitException(String activityToken, Exception cause) {
        super(cause);
        this.activityToken = activityToken;
    }

    /**
     * Gets the list of activities that were being displayed, but doesn't contain parameters.
     */
    public String getGoToList() {
        return goToList;
    }

    /**
     * Called by SlottedController to provide the list of places without parameters.
     * @param goToList
     */
    public void setGoToList(String goToList) {
        this.goToList = goToList;
    }

    /**
     * Gets the token that represents the Activity which will contain tokenized parameters.
     */
    public String getActivityToken() {
        return activityToken;
    }

    /**
     * Gets the cause exception.
     */
    public Exception getWrappedException() {
        return (Exception) getCause();
    }
}
