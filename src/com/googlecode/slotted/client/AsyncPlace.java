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

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.Callback;

//todo javadoc
abstract public class AsyncPlace extends SlottedPlace {

    abstract public void getAsyncActivity(Callback<? super Activity, ? super Throwable> callback);

    @Override public final Activity getActivity() {
        throw new UnsupportedOperationException("This shouldn't be called except for SlottedPlace.getAsynActivity()");
    }

    @Override protected void runGetActivity(Callback<? super Activity, ? super Throwable> callback) {
        getAsyncActivity(callback);
    }
}
