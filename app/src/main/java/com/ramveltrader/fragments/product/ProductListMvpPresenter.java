package com.ramveltrader.fragments.product;


import com.ramveltrader.presenter.MvpPresenter;

public interface ProductListMvpPresenter<V extends ProductListMvp> extends MvpPresenter<V> {

    void pickProducts();

    void loadNextPage();
}
