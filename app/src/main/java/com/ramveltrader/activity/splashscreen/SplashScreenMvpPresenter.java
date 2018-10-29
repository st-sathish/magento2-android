package com.ramveltrader.activity.splashscreen;


import com.ramveltrader.presenter.MvpPresenter;

public interface SplashScreenMvpPresenter <V extends SplashScreenMvpView> extends MvpPresenter<V> {

    void fetchCategories();
}
