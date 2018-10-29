package com.ramveltrader.fragments.category;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.ramveltrader.R;
import com.ramveltrader.activity.landingpage.LandingPageActivity;
import com.ramveltrader.adapters.ExpCategoryAdapter;
import com.ramveltrader.data.SessionStore;
import com.ramveltrader.data.network.models.CategoryResponse;
import com.ramveltrader.fragments.BaseFragment;
import com.ramveltrader.listeners.OnItemClickListener;
import com.ramveltrader.utils.AppConstants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpCategoryFragment extends BaseFragment implements ExpCategoryMvpView, OnItemClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ExpCategoryMvpPresenter<ExpCategoryMvpView> mPresenter = new ExpCategoryPresenter<>();

    public ExpCategoryFragment() {

    }

    public static ExpCategoryFragment newInstance(String title) {
        ExpCategoryFragment expCategoryFragment = new ExpCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.INTENT_PARAM_ONE, title);
        expCategoryFragment.setArguments(bundle);
        return expCategoryFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_exp_category, container, false);
        setUnBinder(ButterKnife.bind(this, view));
        mPresenter.onAttach(this);
        setRecyclerViewAdapter();
        return view;
    }

    public void setRecyclerViewAdapter() {
        ExpCategoryAdapter categoryAdapter = new ExpCategoryAdapter(getActivity(), SessionStore.sSelectedExpandableCategory, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(categoryAdapter);
    }

    @Override
    public void onItemClick(View v, int position) {
        CategoryResponse categoryResponse = SessionStore.sSelectedExpandableCategory.get(position);
        SessionStore.sSelectedCategory = categoryResponse;
        switchFragment(LandingPageActivity.FRAGMENT_DETAIL_LIST_PRODUCT, categoryResponse.getName(), true);
    }
}
