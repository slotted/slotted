package com.googlecode.slotted.gin_codesplitting.client;

import javax.inject.Singleton;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Provides;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.slotted.client.AutoHistoryMapper;
import com.googlecode.slotted.client.SlottedController;
import com.googlecode.slotted.client.SlottedEventBus;

public class AppGinModule extends AbstractGinModule {
    @Override
    protected void configure() {
        bind(EventBus.class).to(SlottedEventBus.class).in(Singleton.class);
        bind(BaseView.class).to(BaseViewImpl.class).in(Singleton.class);
    }

    @Provides @Singleton
    public SlottedController getSlottedController(AutoHistoryMapper mapper, EventBus eventBus) {
        return new SlottedController(mapper, eventBus);
    }

}
