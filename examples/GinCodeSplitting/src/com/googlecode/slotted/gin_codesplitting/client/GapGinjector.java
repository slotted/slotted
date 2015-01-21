package com.googlecode.slotted.gin_codesplitting.client;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.googlecode.slotted.client.SingletonModule;

@GinModules({AppGinModule.class, SingletonModule.class})
public interface GapGinjector extends Ginjector {
    GoodbyeActivity getGoodbyeActivity();
    HelloActivity getHelloActivity();
}