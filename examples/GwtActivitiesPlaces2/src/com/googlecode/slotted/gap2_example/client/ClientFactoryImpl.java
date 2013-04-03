package com.googlecode.slotted.gap2_example.client;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.googlecode.slotted.client.AutoHistoryMapper;
import com.googlecode.slotted.client.HistoryMapper;
import com.googlecode.slotted.client.SlottedController;

public class ClientFactoryImpl implements ClientFactory {
    private final EventBus eventBus = new SimpleEventBus();
    //private final AppHistoryMapper appHistoryMapper = new AppHistoryMapper();
    private final HistoryMapper appHistoryMapper = GWT.create(AutoHistoryMapper.class);
    private final SlottedController placeController;
    private final BaseView baseView = new BaseViewImpl();
    private final HelloView helloView = new HelloViewImpl();
    private final GoodbyeView goodbyeView = new GoodbyeViewImpl();

    public ClientFactoryImpl() {
        placeController = new SlottedController(appHistoryMapper, eventBus);
    }

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