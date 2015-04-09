package com.googlecode.slotted.layout_example.client.header_leftbar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedActivity;

public class HeaderActivity extends SlottedActivity {
    interface MyUiBinder extends UiBinder<Widget, HeaderActivity> {}
    private static MyUiBinder ourUiBinder = GWT.create(MyUiBinder.class);
    @UiField SimpleLayoutPanel headerSlot;
    @UiField Button menuButton;

    private MenuHandler menuHandler;
    private boolean menuVisible = false;

    @Override public void start(AcceptsOneWidget panel) {
        panel.setWidget(ourUiBinder.createAndBindUi(this));
    }

    @Override public AcceptsOneWidget getChildSlotDisplay(Slot slot) {
        return headerSlot;
    }

    public void registerMenuHandler(MenuHandler menuHandler) {
        this.menuHandler = menuHandler;
        menuVisible = false;
        menuButton.setVisible(true);
    }

    public void removeMenuHandler() {
        menuHandler = null;
        menuButton.setVisible(false);
    }

    @UiHandler("menuButton") public void handleMenuClick(ClickEvent event) {
        if (menuHandler != null) {
            menuVisible = !menuVisible;
            menuHandler.setMenuVisible(menuVisible);
        }
    }

    public interface MenuHandler {
        public void setMenuVisible(boolean visible);
    }
}
