package com.googlecode.slotted.gin_codesplitting.client;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

@GinModules({BaseGinModule.class})
public interface BaseGinjector extends Ginjector {
    AsyncProvider<BaseActivity> getBaseActivity();
}