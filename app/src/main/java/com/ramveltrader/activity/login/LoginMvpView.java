package com.ramveltrader.activity.login;

import android.view.View;

import com.ramveltrader.presenter.MvpView;


public interface LoginMvpView extends MvpView {

    void openLandingPageActivity();

    void onForgotPasswordClick(View v);

    void onNewAccountClick(View v);
}
