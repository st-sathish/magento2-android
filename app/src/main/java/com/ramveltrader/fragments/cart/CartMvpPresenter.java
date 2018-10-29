package com.ramveltrader.fragments.cart;


import com.ramveltrader.data.network.models.CartRequest;
import com.ramveltrader.presenter.MvpPresenter;

public interface CartMvpPresenter <V extends CartMvpView> extends MvpPresenter<V> {

    void getCartItems();

    void addCart(CartRequest request);

    void removeCart(Integer itemId);

    void getProductsBySku(String sku);
}
