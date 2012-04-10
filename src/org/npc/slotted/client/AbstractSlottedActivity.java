/*
 * Copyright 2012 NPC Unlimited
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
package org.npc.slotted.client;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

abstract public class AbstractSlottedActivity implements SlottedActivity {
    @Override public String mayStop() {
        return null;
    }

    @Override public void onStop() {
    }

    @Override public void start(AcceptsOneWidget panel, PlaceParameters params, EventBus eventBus) {
        refresh(panel, params, eventBus);
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }
}
