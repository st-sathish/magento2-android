package com.ramveltrader.activity.otp.received;

import android.view.View;

import com.ramveltrader.presenter.MvpView;


public interface OtpReceiveMvpView extends MvpView {

    void onContinueButtonClick(View v);

    void onExistingUserClick(View v);
}
