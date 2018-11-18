package com.ramveltrader.data.network.address;

import com.ramveltrader.data.SessionStore;
import com.ramveltrader.data.network.ApiEndpoints;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONObject;

import io.reactivex.Observable;

public class AddressApiImpl implements AddressApi {

    @Override
    public Observable<JSONObject> getBillingAddress() {
        return Rx2AndroidNetworking.get(ApiEndpoints.API_BILLING_ADDRESS)
                .addHeaders(ApiEndpoints.HEADER_AUTHORIZATION, "Bearer "+ SessionStore.accessToken)
                .build()
                .getJSONObjectObservable();
    }

    @Override
    public Observable<JSONObject> getShippingAddress() {
        return Rx2AndroidNetworking.get(ApiEndpoints.API_SHIPPING_ADDRESS)
                .addHeaders(ApiEndpoints.HEADER_AUTHORIZATION, "Bearer "+ SessionStore.accessToken)
                .build()
                .getJSONObjectObservable();
    }
}
