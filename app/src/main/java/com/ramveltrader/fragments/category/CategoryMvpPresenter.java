package com.ramveltrader.fragments.category;


import com.ramveltrader.presenter.MvpPresenter;
import com.ramveltrader.presenter.MvpView;

public interface CategoryMvpPresenter<V extends MvpView> extends MvpPresenter<V> {

    void fetchCategories();
}
