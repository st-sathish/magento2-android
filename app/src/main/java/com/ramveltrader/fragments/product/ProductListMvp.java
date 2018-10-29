package com.ramveltrader.fragments.product;


import com.ramveltrader.data.network.models.ProductResponse;
import com.ramveltrader.presenter.MvpView;

import java.util.List;

public interface ProductListMvp extends MvpView {

     void update(List<ProductResponse> productResponses);

     void stopEndlessLoading();

     void showEndlessSpinner();

     void hideEndlessSpinner();
}
