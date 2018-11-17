package com.ramveltrader.activity.landingpage;

import com.androidnetworking.error.ANError;
import com.ramveltrader.data.SessionStore;
import com.ramveltrader.data.network.models.CartListResponse;
import com.ramveltrader.data.network.models.CartRequest;
import com.ramveltrader.data.network.models.CartResponse;
import com.ramveltrader.presenter.BasePresenter;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LandingPagePresenter <V extends LandingPageMvpView> extends BasePresenter<V> implements LandingPageMvpPresenter<V> {

    @Override
    public void getCartList() {
        getDataManager().getCartItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CartListResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CartListResponse cartListResponse) {
                        getMvpView().updateCartBadge(cartListResponse.getItemsCount());
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (!isViewAttached()) {
                            return;
                        }

                        // handle the login error here
                        if (e instanceof ANError) {
                            ANError anError = (ANError) e;
                            if(anError.getErrorCode() == 404 || anError.getErrorCode() == 401) {
                                return;
                            }
                            handleApiError(anError);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void addCart(final CartRequest request) {
        if(SessionStore.quoteId == 0) {
            getDataManager().createEmptyCart()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(String quoteId) {
                            SessionStore.quoteId = Integer.parseInt(quoteId);
                            addItemToCart(request);
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (!isViewAttached()) {
                                return;
                            }

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
        } else {
            addItemToCart(request);
        }
    }

    private void addItemToCart(CartRequest request) {
       // getMvpView().showLoading();
        request.getCartItem().setQuoteId(SessionStore.quoteId);
        getDataManager().addItemCart(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CartResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CartResponse cartResponse) {
                        getMvpView().cartAddedCallback(cartResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (!isViewAttached()) {
                            return;
                        }

                        // handle the login error here
                        if (e instanceof ANError) {
                            ANError anError = (ANError) e;
                            handleApiError(anError);
                        }
                    }

                    @Override
                    public void onComplete() {
                        // getMvpView().hideLoading();
                    }
                });
    }
}
