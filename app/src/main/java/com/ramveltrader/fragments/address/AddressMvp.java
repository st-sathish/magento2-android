package com.ramveltrader.fragments.address;

import com.ramveltrader.presenter.MvpView;

public interface AddressMvp extends MvpView {

    void addressCallback();

    void orderCallback();

    void shippingBillingAddressCallback(String billingAddress, String shippingAddress);
}
