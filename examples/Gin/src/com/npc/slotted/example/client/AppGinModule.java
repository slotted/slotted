package com.npc.slotted.example.client;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Provides;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.slotted.client.SlottedController;
import com.googlecode.slotted.client.SlottedEventBus;

import javax.inject.Singleton;

public class AppGinModule extends AbstractGinModule {
    @Override
    protected void configure() {
        bind(EventBus.class).to(SlottedEventBus.class).in(Singleton.class);

        bind(BaseView.class).to(BaseViewImpl.class).in(Singleton.class);
        bind(GoodbyeView.class).to(GoodbyeViewImpl.class).in(Singleton.class);
        bind(HelloView.class).to(HelloViewImpl.class).in(Singleton.class);
    }

    @Provides @Singleton
    public SlottedController getSlottedController(EventBus eventBus) {
        return new SlottedController(new AppHistoryMapper(), eventBus);
    }

}
