package com.ramveltrader.fragments.mycart;


import com.ramveltrader.data.network.models.CartRequest;
import com.ramveltrader.data.network.models.CartRequest2;
import com.ramveltrader.presenter.MvpPresenter;

public interface MyCartMvpPresenter<V extends MyCartMvpView> extends MvpPresenter<V> {

    void getCartItems();

    void removeCart(Integer itemId);

    void addItemToCart(CartRequest request);

    void updateItemToCart(CartRequest2 request, String itemId);

}
