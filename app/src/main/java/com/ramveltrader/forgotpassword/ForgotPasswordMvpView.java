package com.ramveltrader.forgotpassword;

import android.view.View;

import com.ramveltrader.presenter.MvpView;


public interface ForgotPasswordMvpView extends MvpView {

    void onExistingUserClick();

    void onContinueButtonClick(View v);

    void onForgotPasswordSuccessEmail();

}
