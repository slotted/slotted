package com.googlecode.slotted.gin_codesplitting.client;

import com.google.gwt.inject.client.AbstractGinModule;

import javax.inject.Singleton;

public class BaseGinModule extends AbstractGinModule {
    @Override
    protected void configure() {
        bind(BaseView.class).to(BaseViewImpl.class).in(Singleton.class);
    }
}
