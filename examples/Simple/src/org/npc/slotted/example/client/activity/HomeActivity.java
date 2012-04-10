package org.npc.slotted.example.client.activity;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HTML;
import com.google.web.bindery.event.shared.EventBus;
import org.npc.slotted.client.AbstractSlottedActivity;
import org.npc.slotted.client.PlaceParameters;

public class HomeActivity extends AbstractSlottedActivity {
    /**
     * Invoked by the SlottedController to refresh an Activity.  This is also called when
     * an Activity is started and doesn't override start().
     */
    @Override
    public void refresh(AcceptsOneWidget panel, PlaceParameters parameters, EventBus eventBus) {
        panel.setWidget(new HTML("Welcome Home!"));
    }
}
