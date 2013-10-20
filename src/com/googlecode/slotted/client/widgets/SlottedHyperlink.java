package com.googlecode.slotted.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Anchor;
import com.googlecode.slotted.client.SlottedController;
import com.googlecode.slotted.client.SlottedPlace;

public class SlottedHyperlink extends Anchor {
    public SlottedHyperlink(SafeHtml html, SlottedPlace place, SlottedPlace... nonDefaultPlaces) {
        this(html.asString(), place, nonDefaultPlaces);
    }

    public SlottedHyperlink(String text, final SlottedPlace place, final SlottedPlace... nonDefaultPlaces) {
        super(text);
        addClickHandler(new ClickHandler() {
            @Override public void onClick(ClickEvent event) {
                SlottedController.instance.goTo(place, nonDefaultPlaces);
            }
        });
    }
}
