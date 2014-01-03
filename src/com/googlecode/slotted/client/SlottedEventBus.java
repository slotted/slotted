/*
* Copyright 2012 Jeffrey Kleiss
*
* This file is based off of the com.google.web.bindery.event.shared.SimpleEventBus but modified
* to allow handlers added during the handling of event to be called on subsequent events.
*
* Copyright 2011 Google Inc.
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

import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.Event.Type;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.EventHelper;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.google.web.bindery.event.shared.UmbrellaException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Replacement for the GWT EventBus, which solves these problems:
 * <p><ul>
 * <li> Complete the processing of an Event before processing an Event fired during processing
 * <li> Allow handlers added during processing to handle Events fired during processing.
 * <ul><p>
 */
public class SlottedEventBus extends EventBus {
    private interface Command {
        void execute();
    }

    private boolean processing;
    LinkedList<Command> fireEventQueue = new LinkedList<Command>();
    LinkedList<Command> addHandlerQueue = new LinkedList<Command>();

    /**
     * Map of event type to map of event source to list of their handlers.
     */
    private final Map<Event.Type<?>, Map<Object, List<?>>> map = new HashMap<Type<?>, Map<Object,
            List<?>>>();


    @Override public <H> HandlerRegistration addHandler(Type<H> type, H handler) {
        return addHandlerToSource(type, null, handler);
    }

    @Override
    public <H> HandlerRegistration addHandlerToSource(final Type<H> type, final Object source,
            final H handler)
    {
        if (type == null) {
            throw new NullPointerException("Cannot add a handler with a null type");
        }
        if (handler == null) {
            throw new NullPointerException("Cannot add a null handler");
        }

        if (processing) {
            addHandlerQueue.add(new Command() {
                @Override public void execute() {
                    doAdd(type, source, handler);
                }
            });
        } else {
            doAdd(type, source, handler);
        }

        return new HandlerRegistration() {
            public void removeHandler() {
                removeHandlerFromSource(type, source, handler);
            }
        };
    }

    @Override public void fireEvent(Event<?> event) {
        fireEventFromSource(event, null);
    }

    @Override public void fireEventFromSource(final Event<?> event, final Object source) {
        if (event == null) {
            throw new NullPointerException("Cannot fire null event");
        }

        fireEventQueue.add(new Command() {
            @Override public void execute() {
                doFire(event, source);
            }
        });
        processQueues();
    }

    public <H> void removeHandlerFromSource(final Type<H> type, final Object source,
            final H handler)
    {
        if (processing) {
            addHandlerQueue.add(new Command() {
                @Override public void execute() {
                    doRemove(type, source, handler);
                }
            });
        } else {
            doRemove(type, source, handler);
        }
    }

    private void processQueues() {
        if (!processing) {
            processing = true;
            try {
                while (!fireEventQueue.isEmpty()) {
                    while (!addHandlerQueue.isEmpty()) {
                        Command add = addHandlerQueue.removeFirst();
                        add.execute();
                    }

                    Command fire = fireEventQueue.removeFirst();
                    fire.execute();
                }
            } finally {
                processing = false;
            }
        }
    }

    private <H> void doAdd(Event.Type<H> type, Object source, H handler) {
        List<H> list = ensureHandlerList(type, source);
        list.add(handler);
    }

    private <H> void doRemove(Event.Type<H> type, Object source, H handler) {
        List<H> list = ensureHandlerList(type, source);
        list.remove(handler);
    }

    private <H> List<H> ensureHandlerList(Event.Type<H> type, Object source) {
        Map<Object, List<?>> sourceMap = map.get(type);
        if (sourceMap == null) {
            sourceMap = new HashMap<Object, List<?>>();
            map.put(type, sourceMap);
        }

        // safe, we control the puts.
        @SuppressWarnings("unchecked") List<H> handlers = (List<H>) sourceMap.get(source);
        if (handlers == null) {
            handlers = new ArrayList<H>();
            sourceMap.put(source, handlers);
        }

        return handlers;
    }

    private <H> void doFire(Event<H> event, Object source) {
        if (source != null) {
            EventHelper.setSource(event, source);
        }

        List<H> handlers = getDispatchList(event.getAssociatedType(), source);
        Set<Throwable> causes = null;

        for (H handler : handlers) {
            try {
                EventHelper.dispatch(event, handler);
            } catch (Throwable e) {
                if (causes == null) {
                    causes = new HashSet<Throwable>();
                }
                causes.add(e);
            }
        }

        if (causes != null) {
            throw new UmbrellaException(causes);
        }
    }

    private <H> List<H> getDispatchList(Event.Type<H> type, Object source) {
        List<H> directHandlers = getHandlerList(type, source);
        if (source == null) {
            return directHandlers;
        }

        List<H> globalHandlers = getHandlerList(type, null);

        List<H> rtn = new ArrayList<H>(directHandlers);
        rtn.addAll(globalHandlers);
        return rtn;
    }

    private <H> List<H> getHandlerList(Event.Type<H> type, Object source) {
        Map<Object, List<?>> sourceMap = map.get(type);
        if (sourceMap == null) {
            return Collections.emptyList();
        }

        // safe, we control the puts.
        @SuppressWarnings("unchecked") List<H> handlers = (List<H>) sourceMap.get(source);
        if (handlers == null) {
            return Collections.emptyList();
        }

        return handlers;
    }
}
