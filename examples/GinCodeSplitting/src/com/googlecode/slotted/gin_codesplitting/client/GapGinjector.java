package com.googlecode.slotted.gin_codesplitting.client;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

@GinModules({GapGinModule.class})
public interface GapGinjector extends Ginjector {
    GoodbyeActivity getGoodbyeActivity();
    HelloActivity getHelloActivity();
}