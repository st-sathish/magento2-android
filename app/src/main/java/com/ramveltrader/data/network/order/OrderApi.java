package com.ramveltrader.data.network.order;


import com.ramveltrader.data.network.models.AddressModel;
import com.ramveltrader.data.network.models.OrderRequest;

import org.json.JSONObject;

import io.reactivex.Observable;

public interface OrderApi {

    Observable<String> placeOrder(OrderRequest order);

    Observable<JSONObject> setAddress(AddressModel address);

}
