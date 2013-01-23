package com.googlecode.slotted.gin_example.client;

import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.googlecode.slotted.client.SlottedController;

public class ClientFactoryImpl implements ClientFactory {
    private final EventBus eventBus = new SimpleEventBus();
    private final AppHistoryMapper appHistoryMapper = new AppHistoryMapper();
    private final SlottedController placeController = new SlottedController(appHistoryMapper, eventBus);
    private final BaseView baseView = new BaseViewImpl();
    private final HelloView helloView = new HelloViewImpl();
    private final GoodbyeView goodbyeView = new GoodbyeViewImpl();

    @Override
    public EventBus getEventBus() {
        return eventBus;
    }

    public SlottedController getPlaceController() {
        return placeController;
    }

    public BaseView getBaseView() {
        return baseView;
    }

    public HelloView getHelloView() {
        return helloView;
    }

    public GoodbyeView getGoodbyeView() {
        return goodbyeView;
    }
}