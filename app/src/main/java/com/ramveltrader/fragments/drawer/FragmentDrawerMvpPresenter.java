package com.ramveltrader.fragments.drawer;


import com.ramveltrader.presenter.MvpPresenter;

public interface FragmentDrawerMvpPresenter <V extends FragmentDrawerMvpView> extends MvpPresenter<V> {

    void fetchCategories();
}
