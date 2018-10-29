package com.ramveltrader.forgotpassword;

import com.androidnetworking.error.ANError;
import com.ramveltrader.R;
import com.ramveltrader.data.network.security.SecurityApi;
import com.ramveltrader.data.network.security.SecurityApiImpl;
import com.ramveltrader.presenter.BasePresenter;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ForgotPasswordPresenter<V extends ForgotPasswordMvpView> extends BasePresenter<V> implements ForgotPasswordMvpPresenter<V> {

    SecurityApi securityApi;


    public ForgotPasswordPresenter() {
        securityApi = new SecurityApiImpl();
    }

    public void onResetPasswordClick(String email) {
        if (email == null || email.isEmpty()) {
            getMvpView().onError(R.string.empty_email);
        }

        getMvpView().showLoading();
        Map<String, String> body = new HashMap<>();
        body.put("email", email);
        body.put("template", "email_reset");
        body.put("websiteId", "1");
        securityApi.forgotPassword(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean response) {
                        getMvpView().hideLoading();
                        if(response) {
                            getMvpView().onForgotPasswordSuccessEmail();
                        }
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

                   }
                });
    }

}
