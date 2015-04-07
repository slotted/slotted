package com.googlecode.slotted.layout_example.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public class LayoutStyle {
    public static final Resources res = GWT.create(Resources.class);
    public static final Css css = res.css();
    static {css.ensureInjected();}

    public static Resources res() {
        return res;
    }

    public static Css css() {
        return css;
    }

	public static interface Resources extends ClientBundle {
        @Source("LayoutStyle.css")
        Css css();
	}

	public static interface Css extends CssResource {
        String rootSlot();
    }
}
