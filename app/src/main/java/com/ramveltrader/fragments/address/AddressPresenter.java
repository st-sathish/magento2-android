package com.ramveltrader.fragments.address;

import com.androidnetworking.error.ANError;
import com.ramveltrader.data.SessionStore;
import com.ramveltrader.data.network.models.AddressModel;
import com.ramveltrader.data.network.models.OrderRequest;
import com.ramveltrader.presenter.BasePresenter;
import com.ramveltrader.utils.extras.AppLog;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

public class AddressPresenter<V extends AddressMvp> extends BasePresenter<V> implements AddressMvpPresenter<V>{

    private static final String TAG = "AddressPresenter";

    @Override
    public void placeOrder(OrderRequest order) {

            getMvpView().showLoading();

            getDataManager().placeOrder(order)
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
                            getMvpView().orderCallback();
                        }
                    });



    }

    @Override
    public void setAddress(AddressModel address) {

        getMvpView().showLoading();

        getDataManager().setAddress(address)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(JSONObject accessToken) {

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
                        getMvpView().addressCallback();
                    }
                });



    }

    @Override
    public void getAddress() {
        getMvpView().showLoading();
        Observable.zip(getDataManager().getBillingAddress(),
                getDataManager().getShippingAddress(),
                new BiFunction<JSONObject, JSONObject, JSONObject>() {
                    @Override
                    public JSONObject apply(JSONObject billingAddress, JSONObject shippingAddress) {
                        return combineAddress(billingAddress, shippingAddress);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        getMvpView().hideLoading();
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
                    public void onNext(JSONObject result) {
                        getMvpView().hideLoading();
                        try {
                            String billingAddress = joinBillingAddress(result.getJSONObject("billing_address"));
                            String shippingAddress = joinShippingAddress(result.getJSONObject("shipping_address"));
                            getMvpView().shippingBillingAddressCallback(billingAddress, shippingAddress);
                        } catch (JSONException e) {
                            AppLog.error(TAG, e.getMessage());
                        }
                    }
                });
    }

    private String joinBillingAddress(JSONObject address) {
        return joinAddress(address);
    }

    private String joinShippingAddress(JSONObject address) {
        return joinAddress(address);
    }

    private String joinAddress(JSONObject address) {
        String adress = "";
        try {
            String firstName = address.getString("firstname");
            String lastName = address.getString("lastname");
            String city = address.getString("city");
            String postalCode = address.getString("postcode");
            adress = firstName + " " + lastName + "," +city + "," +postalCode;
        } catch (JSONException e) {
            AppLog.error(TAG, e.getMessage());
        }
        return adress;
    }

    private JSONObject combineAddress(JSONObject billingAddress, JSONObject shippingAddress) {
        JSONObject result = new JSONObject();
        try {
            result.put("billing_address", billingAddress);
            result.put("shipping_address", shippingAddress);
        } catch (JSONException e) {
            AppLog.error(TAG, e.getMessage());
        }
        return result;
    }
}
