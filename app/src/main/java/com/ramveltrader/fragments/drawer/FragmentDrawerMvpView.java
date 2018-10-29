package com.ramveltrader.fragments.drawer;

import com.ramveltrader.data.network.models.CategoryResponse;
import com.ramveltrader.presenter.MvpView;

import java.util.List;

public interface FragmentDrawerMvpView extends MvpView {

    void setNavMenuItem(List<CategoryResponse> categoryItems);
}
