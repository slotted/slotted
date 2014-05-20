package com.googlecode.slotted.activity_cache.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.googlecode.slotted.client.SlottedActivity;

public class CacheActivity extends SlottedActivity {

    private Label titleLBL;
    private boolean flag;

    @Override
    public void start(AcceptsOneWidget panel) {
        DockLayoutPanel mainPNL = new DockLayoutPanel(Unit.PX);
        panel.setWidget(mainPNL);

        final CachePlace place = getCurrentPlace(CachePlace.class);
        flag = place.getFlag();

        titleLBL = new Label("New:" + flag);
        mainPNL.addNorth(titleLBL, 40);

        VerticalPanel buttons = new VerticalPanel();
        mainPNL.addWest(buttons, 100);

        Button homeBTN = new Button("Home");
        buttons.add(homeBTN);
        homeBTN.addClickHandler(new ClickHandler() {
            @Override public void onClick(ClickEvent event) {
                ActivityCache.slottedController.goTo(new HomePlace());
            }
        });

        Button cacheBTN = new Button("Cache " + false);
        buttons.add(cacheBTN);
        cacheBTN.addClickHandler(new ClickHandler() {
            @Override public void onClick(ClickEvent event) {
                ActivityCache.slottedController.goTo(new CachePlace(false));
            }
        });

        cacheBTN = new Button("Cache " + true);
        buttons.add(cacheBTN);
        cacheBTN.addClickHandler(new ClickHandler() {
            @Override public void onClick(ClickEvent event) {
                ActivityCache.slottedController.goTo(new CachePlace(true));
            }
        });

        cacheBTN = new Button("No Cache");
        buttons.add(cacheBTN);
        cacheBTN.addClickHandler(new ClickHandler() {
            @Override public void onClick(ClickEvent event) {
                ActivityCache.slottedController.goTo(new NoCachePlace());
            }
        });

        ScrollPanel testSCR = new ScrollPanel();
        mainPNL.add(testSCR);

        VerticalPanel testContentPNL = new VerticalPanel();
        testSCR.setWidget(testContentPNL);
        for (int i = 0; i < 100; i++) {
            testContentPNL.add(new Label("" + i));
        }
    }

    @Override public void onRefresh() {
        titleLBL.setText("Existing:" + flag);
    }

    @Override public void onStop() {
        super.onStop();
    }
}
