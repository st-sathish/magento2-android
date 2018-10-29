package com.ramveltrader.activity.login;


import com.ramveltrader.presenter.MvpPresenter;

public interface LoginMvpPresenter <V extends LoginMvpView> extends MvpPresenter<V> {

    void onLoginBtnClick(String email, String password);

}
