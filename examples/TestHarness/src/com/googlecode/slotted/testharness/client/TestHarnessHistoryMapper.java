package com.googlecode.slotted.testharness.client;

import com.googlecode.slotted.client.HistoryMapper;
import com.googlecode.slotted.testharness.client.flow.A1a1aPlace;
import com.googlecode.slotted.testharness.client.flow.A1aPlace;
import com.googlecode.slotted.testharness.client.flow.APlace;
import com.googlecode.slotted.testharness.client.flow.B1aPlace;
import com.googlecode.slotted.testharness.client.flow.B1bPlace;
import com.googlecode.slotted.testharness.client.flow.B2aPlace;
import com.googlecode.slotted.testharness.client.flow.BPlace;
import com.googlecode.slotted.testharness.client.flow.GoTo1aPlace;
import com.googlecode.slotted.testharness.client.flow.GoTo1bPlace;
import com.googlecode.slotted.testharness.client.flow.GoTo2aPlace;
import com.googlecode.slotted.testharness.client.flow.GoTo2bPlace;
import com.googlecode.slotted.testharness.client.flow.GoToPlace;
import com.googlecode.slotted.testharness.client.flow.HomePlace;
import com.googlecode.slotted.testharness.client.flow.Loading1aPlace;
import com.googlecode.slotted.testharness.client.flow.LoadingPlace;
import com.googlecode.slotted.testharness.client.flow.OnCancelPlace;

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
        registerPlace(new GoTo1aPlace());
        registerPlace(new GoTo1bPlace());
        registerPlace(new GoTo2aPlace());
        registerPlace(new GoTo2bPlace());
        registerPlace(new GoToPlace());
        registerPlace(new Loading1aPlace());
        registerPlace(new LoadingPlace());

    }
}
