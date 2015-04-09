package com.googlecode.slotted.gin_codesplitting.client;

import javax.inject.Singleton;

import com.google.gwt.inject.client.AbstractGinModule;

public class AppGinModule extends AbstractGinModule {
    @Override
    protected void configure() {
        bind(BaseView.class).to(BaseViewImpl.class).in(Singleton.class);
    }
}
