package com.ramveltrader.activity.signup;


import com.ramveltrader.presenter.MvpPresenter;

public interface SignUpMvpPresenter<V extends SignUpMvpView> extends MvpPresenter<V> {

    void onRegisterBtnClick(String email, String firstName, String lastName, String password, String phone);
}
