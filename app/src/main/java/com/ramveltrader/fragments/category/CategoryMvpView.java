package com.ramveltrader.fragments.category;


import com.ramveltrader.data.network.models.CategoryResponse;
import com.ramveltrader.presenter.MvpView;

import java.util.List;

public interface CategoryMvpView extends MvpView {

    void categoryResCallback(List<CategoryResponse> categoryItems);
}
