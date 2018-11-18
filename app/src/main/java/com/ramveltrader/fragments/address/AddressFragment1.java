package com.ramveltrader.fragments.address;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ramveltrader.R;
import com.ramveltrader.fragments.BaseFragment;
import com.ramveltrader.utils.AppConstants;

import butterknife.ButterKnife;

public class AddressFragment1 extends BaseFragment  {

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
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
