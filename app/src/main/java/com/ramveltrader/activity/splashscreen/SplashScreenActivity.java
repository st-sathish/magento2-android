package com.ramveltrader.activity.splashscreen;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.ramveltrader.R;
import com.ramveltrader.activity.BaseAppCompatActivity;
import com.ramveltrader.activity.login.LoginActivity;

public class SplashScreenActivity extends BaseAppCompatActivity implements SplashScreenMvpView {

    private SplashScreenPresenter mPresenter = new SplashScreenPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_splash_screen);
        this.splash();
    }

    public void startLoginIntent() {
        Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void splash() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startLoginIntent();
            }
        }, 2000);
    }
}
