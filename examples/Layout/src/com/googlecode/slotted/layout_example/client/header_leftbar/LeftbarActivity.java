package com.googlecode.slotted.layout_example.client.header_leftbar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.slotted.client.Slot;
import com.googlecode.slotted.client.SlottedActivity;
import com.googlecode.slotted.client.SlottedController;
import com.googlecode.slotted.client.widgets.SlottedNavWidgetHelper;
import com.googlecode.slotted.layout_example.client.header_leftbar.HeaderActivity.MenuHandler;

public class LeftbarActivity extends SlottedActivity implements MenuHandler {
    public interface Style extends CssResource {
        String buttonActive();
    }

    interface MyUiBinder extends UiBinder<Widget, LeftbarActivity> {}
    private static MyUiBinder ourUiBinder = GWT.create(MyUiBinder.class);
    @UiField Style style;
    @UiField SimpleLayoutPanel leftbarSlot;
    @UiField Button content1Button;
    @UiField Button content2Button;
    @UiField DockLayoutPanel dockPanel;
    @UiField VerticalPanel leftbarPanel;

    private boolean isPortrait = false;

    @Override public void start(AcceptsOneWidget panel) {
        panel.setWidget(ourUiBinder.createAndBindUi(this));

        ButtonNavWidgetHelper navHelper = new ButtonNavWidgetHelper(getSlottedController(), getEventBus());
        navHelper.addNavWidget(content1Button, new Content1Place());
        navHelper.addNavWidget(content2Button, new Content2Place());

        handleResize();
        Window.addResizeHandler(new ResizeHandler() {
            @Override public void onResize(ResizeEvent event) {
                handleResize();
            }
        });
    }

    @Override public AcceptsOneWidget getChildSlotDisplay(Slot slot) {
        if (slot == LeftbarPlace.LeftbarSlot) {
            return leftbarSlot;
        }
        return super.getChildSlotDisplay(slot);
    }

    private void handleResize() {
        if (Window.getClientWidth() < 768) {
            if (!isPortrait) {
                isPortrait = true;
                getCurrentActivity(HeaderActivity.class).registerMenuHandler(this);
                setMenuVisible(false);
            }
        } else if (isPortrait) {
            isPortrait = false;
            getCurrentActivity(HeaderActivity.class).removeMenuHandler();
            setMenuVisible(true);
        }
    }

    @Override public void setMenuVisible(boolean visible) {
        if (visible) {
            dockPanel.setWidgetSize(leftbarPanel, 200);
            dockPanel.animate(1000);
        } else {
            dockPanel.setWidgetSize(leftbarPanel, 0);
            dockPanel.animate(1000);
        }
    }


    private class ButtonNavWidgetHelper extends SlottedNavWidgetHelper<Button> {
        public ButtonNavWidgetHelper(SlottedController slottedController, EventBus eventBus) {
            super(slottedController, eventBus);
        }

        @Override protected void handlePlaceActive(Button widget, Place place, boolean active) {
            if (active) {
                widget.addStyleDependentName(style.buttonActive());
            } else {
                widget.removeStyleName(style.buttonActive());
            }
        }
    }
}
