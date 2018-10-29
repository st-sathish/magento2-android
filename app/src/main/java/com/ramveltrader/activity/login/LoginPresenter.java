package com.ramveltrader.activity.login;

import com.androidnetworking.error.ANError;
import com.ramveltrader.R;
import com.ramveltrader.data.SessionStore;
import com.ramveltrader.presenter.BasePresenter;
import com.ramveltrader.utils.ValidationUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter <V extends LoginMvpView> extends BasePresenter<V> implements LoginMvpPresenter<V> {

    @Override
    public void onLoginBtnClick(String email, String password) {
        if (email == null || email.isEmpty()) {
            getMvpView().onError(R.string.empty_email);
            return;
        }
        if(!ValidationUtils.isEmailValid(email)) {
            getMvpView().onError(R.string.invalid_email);
            return;
        }
        if (password == null || password.isEmpty()) {
            getMvpView().onError(R.string.empty_password);
            return;
        }
        getMvpView().showLoading();
        Map<String, String> body = new HashMap<>();
        body.put("username", email);
        body.put("password", password);
        getDataManager().login(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String accessToken) {
                        SessionStore.accessToken = accessToken;
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (!isViewAttached()) {
                            return;
                        }

                        getMvpView().hideLoading();

                        // handle the login error here
                        if (e instanceof ANError) {
                            ANError anError = (ANError) e;
                            handleApiError(anError);
                        }
                    }

                    @Override
                    public void onComplete() {
                        getMvpView().hideLoading();
                        getMvpView().openLandingPageActivity();
                    }
                });
    }
}
