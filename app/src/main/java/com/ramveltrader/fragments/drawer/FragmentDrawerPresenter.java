package com.ramveltrader.fragments.drawer;

import com.androidnetworking.error.ANError;
import com.ramveltrader.R;
import com.ramveltrader.data.network.ApiEndpoints;
import com.ramveltrader.data.network.models.CategoryResponse;
import com.ramveltrader.presenter.BasePresenter;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FragmentDrawerPresenter<V extends FragmentDrawerMvpView> extends BasePresenter<V> implements FragmentDrawerMvpPresenter<V> {

    public FragmentDrawerPresenter() {

    }

    @Override
    public void fetchCategories() {
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
                        getMvpView().setNavMenuItem(categoryResponse.getChildrenData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isViewAttached() && e instanceof ANError) {
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
