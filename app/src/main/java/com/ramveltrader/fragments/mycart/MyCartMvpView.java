package com.ramveltrader.fragments.mycart;


import com.ramveltrader.data.network.models.Address;
import com.ramveltrader.data.network.models.CartListResponse;
import com.ramveltrader.data.network.models.ProductResponse;
import com.ramveltrader.presenter.MvpView;

public interface MyCartMvpView extends MvpView {

    void update(CartListResponse cartListResponses);

    void updateAddress(Address address);

    void getProductCallback(ProductResponse productResponse);

    void removeCartCallback(Boolean b);
}
