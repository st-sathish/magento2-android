package com.ramveltrader.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.ramveltrader.R;
import com.ramveltrader.data.network.models.ProductResponse;
import com.ramveltrader.listeners.OnProductClickListener;
import com.ramveltrader.utils.ProductImageUtils;
import com.ramveltrader.utils.ProductUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ProductResponse> mProductDetails = new ArrayList<>();

    private Context mContext;

    private OnProductClickListener mListener;

    private int mLayout;

    private final int VIEW_TYPE_ITEM = 0;

    private final int VIEW_TYPE_LOADING = 1;

    private boolean showingProgressBar = false;

    public ProductListAdapter(Context context, OnProductClickListener onItemClickListener, int layout) {
        mContext = context;
        this.mListener = onItemClickListener;
        this.mLayout = layout;
    }

    public void update(List<ProductResponse> productDetails) {
        mProductDetails.addAll(productDetails);
        notifyDataSetChanged();
    }

    public void showProgressBar() {
        showingProgressBar = true;
        mProductDetails.add(null);
        notifyItemInserted(mProductDetails.size() - 1);
    }

    public void hideProgressBar() {
        if(showingProgressBar && mProductDetails.size() > 0) {
            mProductDetails.remove(mProductDetails.size() - 1);
            notifyItemRemoved(mProductDetails.size() - 1);
        }
    }

    public ProductResponse getItem(int position) {
        return mProductDetails.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(mLayout, parent, false);
            return new ProductListAdapter.ProductDetailViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ProductDetailViewHolder) {
            ProductDetailViewHolder itemHolder = (ProductDetailViewHolder) holder;
            ProductResponse productDetail = mProductDetails.get(position);
            itemHolder.name.setText(productDetail.getName());
            String price = mContext.getResources().getString(R.string.Rs)+" " +String.valueOf(productDetail.getPrice());
            itemHolder.price.setText(price);
            ProductImageUtils.loadImage(mContext, itemHolder.imageView, productDetail);
        }
        else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mProductDetails.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mProductDetails != null ? mProductDetails.size() : 0;
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.progress_bar)
        ProgressBar progressBar;

        public LoadingViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    class ProductDetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.item_thumb)
        ImageView imageView;

        @BindView(R.id.item_name)
        TextView name;

        @BindView(R.id.item_price)
        TextView price;

        @BindView(R.id.remove_item)
        TextView removeItem;

        @BindView(R.id.add_item)
        TextView addItem;

        @BindView(R.id.item_quantity)
        TextView quantity;

        @BindView(R.id.item_cart)
        ImageView itemCart;

        //@BindView(R.id.item_compare)
        //ImageView itemCompare;

        public ProductDetailViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(this);
            imageView.setOnClickListener(this);
            addItem.setOnClickListener(this);
            removeItem.setOnClickListener(this);
            itemCart.setOnClickListener(this);
            //itemCompare.setOnClickListener(this);
            v.setTag(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.add_item:
                    quantity.setText(ProductUtils.increaseItemQuantity(quantity.getText().toString()));
                    break;
                case R.id.remove_item:
                    quantity.setText(ProductUtils.deceaseItemQuantity(quantity.getText().toString()));
                    break;
                case R.id.item_cart:
                    mListener.onAddCartClick(v, getAdapterPosition(), quantity.getText().toString());
                    break;
                /*case R.id.item_compare:
                    mListener.onCompareClick(v, getAdapterPosition());
                    break;*/
                case R.id.item_thumb:
                    mListener.onOpenProductOverview(v, getAdapterPosition());
                    break;
            }
        }
    }
}
