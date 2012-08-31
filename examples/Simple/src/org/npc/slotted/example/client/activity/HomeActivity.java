package org.npc.slotted.example.client.activity;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.web.bindery.event.shared.EventBus;
import org.npc.slotted.client.PlaceParameters;
import org.npc.slotted.client.Slot;
import org.npc.slotted.client.SlottedActivity;
import org.npc.slotted.client.SlottedController;
import org.npc.slotted.example.client.Simple;
import org.npc.slotted.example.client.place.NestedLevelTwoPlace;
import org.npc.slotted.example.client.place.NestedPlace;
import org.npc.slotted.example.client.place.ParentPlace;

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
                Simple.slottedController.goTo(new ParentPlace());
            }
        });

        String token = Simple.slottedController.createToken(new NestedPlace(),
                new PlaceParameters());
        Hyperlink nestedLNK = new Hyperlink("Goto Nested", token);
        mainPNL.add(nestedLNK);

        String url = Simple.slottedController.createUrl(new NestedLevelTwoPlace(),
                new PlaceParameters());
        Anchor levelTwoACH = new Anchor("Goto Level Two", url);
        mainPNL.add(levelTwoACH);
    }
}
