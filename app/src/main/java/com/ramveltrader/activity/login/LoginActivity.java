package com.ramveltrader.activity.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;


import com.ramveltrader.R;
import com.ramveltrader.activity.BaseAppCompatActivity;
import com.ramveltrader.activity.landingpage.LandingPageActivity;
import com.ramveltrader.activity.signup.SignUpActivity;
import com.ramveltrader.forgotpassword.ForgotPasswordActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by system2 on 22-Dec-17.
 */

public class LoginActivity extends BaseAppCompatActivity implements LoginMvpView {

    @BindView(R.id.edit_email)
    EditText mEmailEditText;

    @BindView(R.id.edit_password)
    EditText mPasswordEditText;

    LoginMvpPresenter<LoginMvpView> mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_login);
        setUnBinder(ButterKnife.bind(this));
        mPresenter = new LoginPresenter<>();
        mPresenter.onAttach(LoginActivity.this);
        mEmailEditText.setText("sukumar.inapp2@gmail.com");
        mPasswordEditText.setText("rails123$");
    }

    @Override
    public void openLandingPageActivity() {
        Intent intent = new Intent(LoginActivity.this, LandingPageActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.button_login)
    public void onLoginBtnClick(View v) {
        mPresenter.onLoginBtnClick(mEmailEditText.getText().toString(),
                mPasswordEditText.getText().toString());
    }

    @OnClick(R.id.forgot_password)
    @Override
    public void onForgotPasswordClick(View v) {
        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.new_account)
    @Override
    public void onNewAccountClick(View v) {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
}
