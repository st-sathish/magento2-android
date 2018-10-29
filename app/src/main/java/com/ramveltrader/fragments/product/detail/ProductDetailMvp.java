package com.ramveltrader.fragments.product.detail;

import com.ramveltrader.data.network.models.ProductResponse;
import com.ramveltrader.presenter.MvpView;

import java.util.List;

public interface ProductDetailMvp extends MvpView {

    void setDescription(String description);

    void setShortDescription(String shortDescription);

    void setProductName(String productName);

    void setProductPrice(String productPrice);

    void onRelatedProductsSuccess(List<ProductResponse> relatedProducts);

    void showHorizontalProgressBar();

    void hideHorizontalProgressBar();

    void updateQuantity(String quantity);

    void switchProductListFragment(Integer addedCount);
}
