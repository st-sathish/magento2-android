package com.ramveltrader.fragments.product.detail;


import com.ramveltrader.data.network.models.ProductResponse;
import com.ramveltrader.presenter.MvpPresenter;
import com.ramveltrader.presenter.MvpView;

import java.util.List;

public interface ProductDetailMvpPresenter <V extends MvpView> extends MvpPresenter<V> {

    void setViewValue(ProductResponse mProductDetail);

    void getProductRelatedLinks(List<ProductResponse.ProductLink> productLinks);

    void increaseQuantity(String quantity);

    void decreaseQuantity(String quantity);

    void addCart(String quantity, String sku);
}
