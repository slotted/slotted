package com.googlecode.slotted.gap2_example.client;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.googlecode.slotted.client.AutoHistoryMapper;
import com.googlecode.slotted.client.SlottedController;

public class ClientFactoryImpl implements ClientFactory {
    private final EventBus eventBus = new SimpleEventBus();
    private final AutoHistoryMapper autoHistoryMapper = GWT.create(AutoHistoryMapper.class);
    private final SlottedController placeController = new SlottedController(autoHistoryMapper, eventBus);
    private final HelloView helloView = new HelloViewImpl();
    private final GoodbyeView goodbyeView = new GoodbyeViewImpl();
    private final BaseView baseView = new BaseViewImpl();

    @Override
    public EventBus getEventBus() {
        return eventBus;
    }

    public SlottedController getPlaceController() {
        return placeController;
    }

    public HelloView getHelloView() {
        return helloView;
    }

    public GoodbyeView getGoodbyeView() {
        return goodbyeView;
    }

    public BaseView getBaseView() {
        return baseView;
    }
}