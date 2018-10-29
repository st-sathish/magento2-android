package com.ramveltrader.data.network.product;


import com.ramveltrader.data.network.models.ProductListResponse;
import com.ramveltrader.data.network.models.ProductResponse;

import io.reactivex.Observable;

public interface ProductApi {

    /**
     * Get product list by api
     * @param pageSize
     *      page size default 10
     * @param currentPage
     *      current page default 1
     * @param categoryId
     *      selected category id
     * @return
     *      list of product detail list
     */
    Observable<ProductListResponse> getProductDetailListByCategoryId(int currentPage, int pageSize, int categoryId);

    /**
     * Product Response
     * @param productSkuId
     * @return
     */
    Observable<ProductResponse> getProductBySku(String productSkuId);
}
