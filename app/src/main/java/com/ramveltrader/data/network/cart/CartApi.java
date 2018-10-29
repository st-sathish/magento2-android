package com.ramveltrader.data.network.cart;


import com.ramveltrader.data.network.models.CartListResponse;
import com.ramveltrader.data.network.models.CartRequest;
import com.ramveltrader.data.network.models.CartRequest2;
import com.ramveltrader.data.network.models.CartResponse;

import io.reactivex.Observable;

public interface CartApi {

    Observable<CartListResponse> getItems();

    Observable<CartResponse> addItem(CartRequest request);

    Observable<CartResponse> updateItem(CartRequest2 request, String itemId);

    Observable<String> createEmpty();

    Observable<Boolean> deleteItem(Integer itemId);
}
