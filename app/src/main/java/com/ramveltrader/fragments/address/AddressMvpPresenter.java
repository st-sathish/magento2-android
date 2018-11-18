package com.ramveltrader.fragments.address;


import com.ramveltrader.data.network.models.AddressModel;
import com.ramveltrader.data.network.models.OrderRequest;
import com.ramveltrader.presenter.MvpPresenter;
import com.ramveltrader.presenter.MvpView;

public interface AddressMvpPresenter<V extends MvpView> extends MvpPresenter<V> {

    void placeOrder(OrderRequest order);

    void setAddress(AddressModel address);

    void getAddress();
}
