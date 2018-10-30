package com.ramveltrader.fragments.category;


import com.androidnetworking.error.ANError;
import com.ramveltrader.data.network.ApiEndpoints;
import com.ramveltrader.data.network.models.CategoryResponse;
import com.ramveltrader.presenter.BasePresenter;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CategoryPresenter<V extends CategoryMvpView> extends BasePresenter<V> implements CategoryMvpPresenter<V> {

    @Override
    public void fetchCategories() {
        getMvpView().showLoading();
        Rx2AndroidNetworking
                .get(ApiEndpoints.API_GET_CATEGORIES)
                .addHeaders("Content-Type", "application/json")
                .build()
                .getObjectObservable(CategoryResponse.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoryResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CategoryResponse categoryResponse) {
                        getMvpView().categoryResCallback(categoryResponse.getChildrenData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().hideLoading();
                        if (isViewAttached() && e instanceof ANError) {
                            ANError anError = (ANError) e;
                            handleApiError(anError);
                        }
                    }

                    @Override
                    public void onComplete() {
                        getMvpView().hideLoading();
                    }
                });
    }
}
