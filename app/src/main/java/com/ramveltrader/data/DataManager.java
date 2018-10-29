package com.ramveltrader.data;


import com.ramveltrader.data.network.models.AddressModel;
import com.ramveltrader.data.network.models.CartListResponse;
import com.ramveltrader.data.network.models.CartRequest;
import com.ramveltrader.data.network.models.CartRequest2;
import com.ramveltrader.data.network.models.CartResponse;
import com.ramveltrader.data.network.models.OrderRequest;
import com.ramveltrader.data.network.models.ProductListResponse;
import com.ramveltrader.data.network.models.ProductResponse;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.Observable;

public interface DataManager {

    Observable<JSONObject> signUp(JSONObject body);

    Observable<String> login(Map<String, String> body);

    Observable<ProductListResponse> getProductDetailListByCategoryId(int currentPage, int pageSize, int categoryId);

    Observable<ProductResponse> getProductBySku(String productSkuId);

    void setAccessToken(String accessToken);

    String getAccessToken();

    Observable<CartListResponse> getCartItems();

    Observable<CartResponse> addItemCart(CartRequest request);

    Observable<CartResponse> updateItemCart(CartRequest2 request, String itemId);

    Observable<String> createEmptyCart();

    Observable<Boolean> deleteCartItem(Integer itemId);

    Observable<String> placeOrder(OrderRequest order);

    Observable<JSONObject> setAddress(AddressModel address);

}
