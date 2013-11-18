//package com.googlecode.slotted.gin_codesplitting.client;
//
//import com.google.gwt.activity.shared.Activity;
//import com.google.gwt.core.client.Callback;
//import com.google.gwt.core.client.GWT;
//import com.google.gwt.core.client.RunAsyncCallback;
//import com.google.gwt.user.client.rpc.AsyncCallback;
//import com.googlecode.slotted.client.GroupProvider;
//import com.googlecode.slotted.client.SlottedPlace;
//
//public class BaseSplitGroup extends GroupProvider {
//
//    private static BaseGinjector baseGinjector;
//
//    @Override public void get(final SlottedPlace place, final Callback<? super Activity, ? super Throwable> callback) {
//        GWT.runAsync(new RunAsyncCallback() {
//            @Override public void onFailure(Throwable reason) {
//                callback.onFailure(reason);
//            }
//
//            @Override public void onSuccess() {
//                if (baseGinjector == null) {
//                    baseGinjector = GWT.create(BaseGinjector.class);
//                }
//
//                if (place instanceof BasePlace) {
//                    baseGinjector.getBaseActivity().get(new AsyncCallback<BaseActivity>() {
//                        @Override public void onFailure(Throwable caught) {
//                            //Todo implement
//                        }
//
//                        @Override public void onSuccess(BaseActivity result) {
//                            callback.onSuccess(result);
//                        }
//                    });
//                }
//            }
//        });
//    }
//}
