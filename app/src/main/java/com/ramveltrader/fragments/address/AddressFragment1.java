package com.ramveltrader.fragments.address;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ramveltrader.R;
import com.ramveltrader.activity.landingpage.LandingPageActivity;
import com.ramveltrader.data.SessionStore;
import com.ramveltrader.data.network.models.OrderRequest;
import com.ramveltrader.fragments.BaseFragment;
import com.ramveltrader.utils.AppConstants;
import com.ramveltrader.utils.extras.AppLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressFragment1 extends BaseFragment implements AddressMvp {

    private static final String TAG = "AddressFragment1";

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

    @OnClick(R.id.btn_place_order)
    public void onOrderClick() {
        OrderRequest order = new OrderRequest();
        OrderRequest.PaymentMethod payment = new OrderRequest.PaymentMethod();
        payment.setMethod("banktransfer");
        order.setPaymentMethod(payment);

        // set billing address
        OrderRequest.BillingAddress bill = new OrderRequest.BillingAddress();
        if(SessionStore.sSetAddress != null) {
            try {
                JSONObject billingAddress = SessionStore.sSetAddress.getJSONObject("billing_address");
                bill.setEmail(SessionStore.sEmail);
                bill.setCity(billingAddress.getString("city"));
                bill.setCountry(billingAddress.getString("country_id"));
                bill.setFirstName(billingAddress.getString("firstname"));
                bill.setLastName(billingAddress.getString("lastname"));
                bill.setPostcode(Integer.parseInt(billingAddress.getString("postcode")));
                bill.setPhone_number(billingAddress.getString("telephone"));
                JSONObject region = billingAddress.getJSONObject("region");
                bill.setRegion(region.getString("region"));
                bill.setRegion_code(region.getString("region_code"));
                bill.setRegion_id(region.getInt("region_id"));
                ArrayList<String> places = new ArrayList<>();
                JSONArray street = billingAddress.getJSONArray("street");
                for (int i=0;i < street.length();i++) {
                    places.add(street.getString(i));
                }
                bill.setStreet(places);
            } catch (JSONException e) {
                AppLog.error(TAG, e.getMessage());
            }
        }
        order.setBillingAddress(bill);

        mvpPresenter.placeOrder(order);
    }

    @OnClick(R.id.btn_back)
    public void onBackClick() {
        goBack();
    }

    @Override
    public void addressCallback() {

    }

    @Override
    public void shippingBillingAddressCallback(String billingAddress, String shippingAddress) {
        mBillingAddress.setText(billingAddress);
        mShippingAddress.setText(shippingAddress);
    }

    @Override
    public void orderCallback() {
        Toast.makeText(getActivity(), "Order is placed.", Toast.LENGTH_LONG).show();
        SessionStore.quoteId = 0;
        resetCartCount();
        //getActivity().recreate();
        switchFragment(LandingPageActivity.FRAGMENT_HOME, "Home", false);
    }
}
