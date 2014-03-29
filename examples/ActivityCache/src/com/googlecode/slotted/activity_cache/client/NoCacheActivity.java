package com.googlecode.slotted.activity_cache.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.googlecode.slotted.client.SlottedActivity;

public class NoCacheActivity extends SlottedActivity {

    /**
     * Invoked by the SlottedController to refresh an Activity.  This is also called when
     * an Activity is started and doesn't override start().
     */
    @Override
    public void start(AcceptsOneWidget panel) {
        VerticalPanel mainPNL = new VerticalPanel();
        panel.setWidget(mainPNL);

        mainPNL.add(new HTML("No Cache"));

        Button homeBTN = new Button("Home");
        mainPNL.add(homeBTN);
        homeBTN.addClickHandler(new ClickHandler() {
            @Override public void onClick(ClickEvent event) {
                ActivityCache.slottedController.goTo(new HomePlace());
            }
        });

        Button cacheBTN = new Button("Cache");
        mainPNL.add(cacheBTN);
        cacheBTN.addClickHandler(new ClickHandler() {
            @Override public void onClick(ClickEvent event) {
                ActivityCache.slottedController.goTo(new CachePlace());
            }
        });

        ScrollPanel testSCR = new ScrollPanel();
        mainPNL.add(testSCR);
        testSCR.setSize("200px", "200px");

        VerticalPanel testContentPNL = new VerticalPanel();
        testSCR.setWidget(testContentPNL);
        for (int i = 0; i < 100; i++) {
            testContentPNL.add(new Label("" + i));
        }
    }
}
