package com.googlecode.slotted.activity_cache.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.googlecode.slotted.client.SlottedActivity;

public class HomeActivity extends SlottedActivity {

    /**
     * Invoked by the SlottedController to refresh an Activity.  This is also called when
     * an Activity is started and doesn't override start().
     */
    @Override
    public void start(AcceptsOneWidget panel) {
        VerticalPanel mainPNL = new VerticalPanel();
        panel.setWidget(mainPNL);

        mainPNL.add(new HTML("Welcome Home!"));

        Button cacheBTN = new Button("Cache");
        mainPNL.add(cacheBTN);
        cacheBTN.addClickHandler(new ClickHandler() {
            @Override public void onClick(ClickEvent event) {
                ActivityCache.slottedController.goTo(new CachePlace());
            }
        });

        Button noCacheBTN = new Button("No Cache");
        mainPNL.add(noCacheBTN);
        noCacheBTN.addClickHandler(new ClickHandler() {
            @Override public void onClick(ClickEvent event) {
                ActivityCache.slottedController.goTo(new NoCachePlace());
            }
        });
    }
}
