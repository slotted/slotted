/*
 * Copyright 2012 Jeffrey Kleiss
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.googlecode.slotted.client;

/**
 * A special SlottedPlace for handling Slotted exceptions.
 */
abstract public class SlottedErrorPlace extends SlottedPlace {
    private Throwable throwable;

    /**
     * Gets the Slotted exception that caused the error page to be displayed
     */
    public Throwable getException() {
        return throwable;
    }

    /**
     * Called by the SlottedController to set the exception that is causing the
     * error page to be displayed.
     */
    public void setException(Throwable throwable) {
        this.throwable = throwable;
    }
}
