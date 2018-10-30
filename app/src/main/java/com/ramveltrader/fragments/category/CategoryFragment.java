package com.ramveltrader.fragments.category;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.ramveltrader.R;
import com.ramveltrader.activity.landingpage.LandingPageActivity;
import com.ramveltrader.adapters.CategoryExpandableListAdapter;
import com.ramveltrader.data.SessionStore;
import com.ramveltrader.data.network.models.CategoryResponse;
import com.ramveltrader.fragments.BaseFragment;
import com.ramveltrader.listeners.OnItemClickListener;
import com.ramveltrader.utils.AppConstants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryFragment extends BaseFragment implements CategoryMvpView, ExpandableListView.OnChildClickListener {

    @BindView(R.id.expandableListView)
    ExpandableListView mExpandableListView;

    private CategoryMvpPresenter<CategoryMvpView> mPresenter;

    private CategoryExpandableListAdapter mCategoryExpandableListAdapter;

    public CategoryFragment() {

    }

    public static CategoryFragment newInstance(String title) {
        CategoryFragment categoryFragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.INTENT_PARAM_ONE, title);
        categoryFragment.setArguments(bundle);
        return categoryFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_category, container, false);
        setUnBinder(ButterKnife.bind(this, view));
        mPresenter = new CategoryPresenter<>();
        mPresenter.onAttach(this);
        setGroupIndicator();
        mCategoryExpandableListAdapter = new CategoryExpandableListAdapter(getActivity());
        mExpandableListView.setAdapter(mCategoryExpandableListAdapter);
        mExpandableListView.setOnChildClickListener(this);
        // make API call to fetch categories
        mPresenter.fetchCategories();
        return view;
    }

    public void setGroupIndicator() {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        mExpandableListView.setIndicatorBounds(width - getPixelFromDips(50), width - getPixelFromDips(10));
    }


    public int getPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id) {
        SessionStore.sSelectedCategory = mCategoryExpandableListAdapter.getChild(groupPosition, childPosition);
        switchFragment(LandingPageActivity.FRAGMENT_DETAIL_LIST_PRODUCT, SessionStore.sSelectedCategory.getName(), true);
        return false;
    }

    @Override
    public void categoryResCallback(List<CategoryResponse> categoryItems) {
        mCategoryExpandableListAdapter.refresh(categoryItems);
    }
}
