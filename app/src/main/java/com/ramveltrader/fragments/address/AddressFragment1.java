package com.ramveltrader.fragments.address;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ramveltrader.R;
import com.ramveltrader.fragments.BaseFragment;
import com.ramveltrader.utils.AppConstants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressFragment1 extends BaseFragment implements AddressMvp {

    AddressPresenter<AddressMvp> mvpPresenter;

    @BindView(R.id.billing_address)
    TextView mBillingAddress;

    @BindView(R.id.shipping_address)
    TextView mShippingAddress;

    public static AddressFragment1 newInstance(String title) {
        AddressFragment1 addressFragment1 = new AddressFragment1();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.INTENT_PARAM_ONE, title);
        addressFragment1.setArguments(bundle);
        return addressFragment1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_address1, container, false);
        setUnBinder(ButterKnife.bind(this, view));
        mvpPresenter = new AddressPresenter<>();
        mvpPresenter.onAttach(this);
        mvpPresenter.getAddress();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        mvpPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void orderCallback() {

    }

    @Override
    public void addressCallback() {

    }

    @Override
    public void shippingBillingAddressCallback(String billingAddress, String shippingAddress) {
        mBillingAddress.setText(billingAddress);
        mShippingAddress.setText(shippingAddress);
    }
}
