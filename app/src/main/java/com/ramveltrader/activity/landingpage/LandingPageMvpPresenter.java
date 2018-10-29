package com.ramveltrader.activity.landingpage;


import com.ramveltrader.data.network.models.CartRequest;
import com.ramveltrader.presenter.MvpPresenter;

public interface LandingPageMvpPresenter <V extends LandingPageMvpView> extends MvpPresenter<V> {

    void getCartList();

    void addCart(final CartRequest request);
}
