package com.ramveltrader.fragments.cart;


import com.ramveltrader.data.network.models.CartResponse;
import com.ramveltrader.data.network.models.ProductResponse;
import com.ramveltrader.presenter.MvpView;

import java.util.List;

public interface CartMvpView extends MvpView {

    void addCartCallback(CartResponse cartResponse);

    void getCartProductsCallback(List<ProductResponse> productResponses);

    void removeCartCallback(Boolean b);

    void getProductCallback(ProductResponse productResponse);

    void updateBadge(Integer qty);

}
