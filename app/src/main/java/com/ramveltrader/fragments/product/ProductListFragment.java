package com.ramveltrader.fragments.product;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import com.ramveltrader.R;
import com.ramveltrader.activity.landingpage.LandingPageActivity;
import com.ramveltrader.adapters.ProductListAdapter;
import com.ramveltrader.data.SessionStore;
import com.ramveltrader.data.network.models.ProductResponse;
import com.ramveltrader.decorators.ItemDecorationGridColumns;
import com.ramveltrader.fragments.BaseFragment;
import com.ramveltrader.listeners.EndlessRecyclerOnScrollListener;
import com.ramveltrader.listeners.OnProductClickListener;
import com.ramveltrader.utils.AppConstants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListFragment extends BaseFragment implements ProductListMvp, OnProductClickListener {

    ProductListMvpPresenter<ProductListMvp> mPresenter = new ProductListPresenter<>();

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    ProductListAdapter productListAdapter = null;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    public ProductListFragment() {

    }

    public static ProductListFragment newInstance(String title) {
        ProductListFragment productListFragment = new ProductListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.INTENT_PARAM_ONE, title);
        productListFragment.setArguments(bundle);
        return productListFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_product_list, container, false);
        setUnBinder(ButterKnife.bind(this, view));
        mPresenter.onAttach(this);
        initializeRecyclerViewAdapter();
        return view;
    }

    @Override
    public void stopEndlessLoading() {
        loadMoreRecord = false;
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.pickProducts();
    }

    @Override
    public void update(List<ProductResponse> productResponses) {
        productListAdapter.update(productResponses);
    }

    public void initializeRecyclerViewAdapter() {
        productListAdapter = new ProductListAdapter(getActivity(), this, R.layout.item_product_list);
        //mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(productListAdapter);
        mRecyclerView.addItemDecoration(new ItemDecorationGridColumns(10, 2));
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                if(loadMoreRecord) {
                    mPresenter.loadNextPage();
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onCompareClick(View v, int position) {
        //SessionStore.productDetail = productListAdapter.getItem(position);
        //switchFragment(LandingPageActivity.FRAGMENT_DETAILS_PRODUCT, "", true);
    }

    @Override
    public void onAddCartClick(View v, int position, String quantity) {
        addToCart(productListAdapter.getItem(position), quantity);
    }

    @Override
    public void onOpenProductOverview(View v, int position) {
        String pName = productListAdapter.getItem(position).getName();
        SessionStore.productDetail = productListAdapter.getItem(position);
        switchFragment(LandingPageActivity.FRAGMENT_PRODUCT_DETAILS, pName, true);
    }

    @Override
    public void showEndlessSpinner() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEndlessSpinner() {
        progressBar.setVisibility(View.INVISIBLE);
    }
}
