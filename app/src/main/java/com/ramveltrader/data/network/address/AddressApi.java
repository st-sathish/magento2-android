package com.ramveltrader.data.network.address;

import org.json.JSONObject;

import io.reactivex.Observable;

public interface AddressApi {

    Observable<JSONObject> getBillingAddress();

    Observable<JSONObject> getShippingAddress();
}
