package com.googlecode.slotted.testharness.client;

import com.googlecode.slotted.client.HistoryMapper;
import com.googlecode.slotted.testharness.client.activity.A1a1aPlace;
import com.googlecode.slotted.testharness.client.activity.A1aPlace;
import com.googlecode.slotted.testharness.client.activity.APlace;
import com.googlecode.slotted.testharness.client.activity.B1aPlace;
import com.googlecode.slotted.testharness.client.activity.B1bPlace;
import com.googlecode.slotted.testharness.client.activity.B2aPlace;
import com.googlecode.slotted.testharness.client.activity.BPlace;
import com.googlecode.slotted.testharness.client.activity.HomePlace;
import com.googlecode.slotted.testharness.client.activity.OnCancelPlace;

public class TestHarnessHistoryMapper extends HistoryMapper {
    @Override
    protected void init() {
        registerDefaultPlace(new HomePlace(), "home");

        registerPlace(new A1a1aPlace(), "level2");
        registerPlace(new A1aPlace());
        registerPlace(new APlace());
        registerPlace(new B1aPlace());
        registerPlace(new B1bPlace());
        registerPlace(new B2aPlace());
        registerPlace(new BPlace());
        registerPlace(new OnCancelPlace());
    }
}
