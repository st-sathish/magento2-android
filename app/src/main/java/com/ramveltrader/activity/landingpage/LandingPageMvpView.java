package com.ramveltrader.activity.landingpage;


import com.ramveltrader.data.network.models.Address;
import com.ramveltrader.data.network.models.CartResponse;
import com.ramveltrader.data.network.models.ProductResponse;
import com.ramveltrader.presenter.MvpView;

public interface LandingPageMvpView extends MvpView {

    void updateCartBadge(int count);

    void doIncrementCartCount(Integer count);

    void addCartToMyAccount(ProductResponse response, String quantity);

    void cartAddedCallback(CartResponse cartResponse);

    void updateAddress(Address address);

    Address getAddress();

    String getCount();
}
