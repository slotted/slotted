package com.googlecode.slotted.gin_codesplitting.client;

import javax.inject.Singleton;

import com.google.gwt.inject.client.AbstractGinModule;

public class GapGinModule extends AbstractGinModule {
    @Override
    protected void configure() {
        bind(GoodbyeView.class).to(GoodbyeViewImpl.class).in(Singleton.class);
        bind(HelloView.class).to(HelloViewImpl.class).in(Singleton.class);
    }
}
