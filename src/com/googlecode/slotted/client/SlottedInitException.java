package com.googlecode.slotted.client;

public class SlottedInitException extends SlottedException {

    private String goToList;
    private String activityToken;

    private SlottedInitException() {}

    public SlottedInitException(String activityToken, Exception cause) {
        super(cause);
        this.activityToken = activityToken;
    }

    public String getGoToList() {
        return goToList;
    }

    public void setGoToList(String goToList) {
        this.goToList = goToList;
    }

    public String getActivityToken() {
        return activityToken;
    }

    public Exception getWrappedException() {
        return (Exception) getCause();
    }
}
