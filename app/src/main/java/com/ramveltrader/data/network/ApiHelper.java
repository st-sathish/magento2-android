package com.ramveltrader.data.network;


import com.ramveltrader.data.network.address.AddressApi;
import com.ramveltrader.data.network.cart.CartApi;
import com.ramveltrader.data.network.order.OrderApi;
import com.ramveltrader.data.network.product.ProductApi;
import com.ramveltrader.data.network.security.SecurityApi;

public interface ApiHelper {

    SecurityApi getSecurityApi();

    ProductApi getProductApi();

    AppApiHeader getApiHeader();

    CartApi getCartApi();

    OrderApi getOrderApi();

    AddressApi getAddressApi();
}
