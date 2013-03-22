package com.googlecode.slotted.testharness.client.activity;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.googlecode.slotted.client.SlottedActivity;
import com.googlecode.slotted.testharness.client.TestHarness;

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

        Button parentBTN = new Button("Goto Parent");
        mainPNL.add(parentBTN);
        parentBTN.addClickHandler(new ClickHandler() {
            @Override public void onClick(ClickEvent event) {
                TestHarness.slottedController.goTo(new APlace());
            }
        });

        String token = TestHarness.slottedController.createToken(new A1aPlace());
        Hyperlink nestedLNK = new Hyperlink("Goto Nested", token);
        mainPNL.add(nestedLNK);

        String url = TestHarness.slottedController.createUrl(new A1a1aPlace());
        Anchor levelTwoACH = new Anchor("Goto Level Two", url);
        mainPNL.add(levelTwoACH);
    }
}
