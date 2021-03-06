package com.ramveltrader.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.ramveltrader.R;
import com.ramveltrader.data.network.models.ProductResponse;
import com.ramveltrader.listeners.OnRelatedProductClickListener;
import com.ramveltrader.utils.ProductImageUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RelatedProductListAdapter extends RecyclerView.Adapter<RelatedProductListAdapter.RelatedProductViewHolder> {

    private List<ProductResponse> mProductDetails = new ArrayList<>();

    private Context mContext;

    private OnRelatedProductClickListener mListener;

    public RelatedProductListAdapter(Context context, OnRelatedProductClickListener onItemClickListener) {
        mContext = context;
        this.mListener = onItemClickListener;
    }

    public void update(List<ProductResponse> productDetails) {
        mProductDetails.addAll(productDetails);
        notifyDataSetChanged();
    }

    public ProductResponse getItem(int position) {
        return mProductDetails.get(position);
    }

    @Override
    public RelatedProductListAdapter.RelatedProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_product_horizontal, parent, false);
        return new RelatedProductListAdapter.RelatedProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RelatedProductListAdapter.RelatedProductViewHolder itemHolder, int position) {
        ProductResponse productDetail = mProductDetails.get(position);
        itemHolder.name.setText(productDetail.getName());
        String strPrice = mContext.getResources().getString(R.string.Rs)+" " +String.valueOf(productDetail.getPrice());
        itemHolder.price.setText(strPrice);
        ProductImageUtils.loadImage(mContext, itemHolder.imageView, productDetail);
    }

    @Override
    public int getItemCount() {
        return mProductDetails != null ? mProductDetails.size() : 0;
    }

    class RelatedProductViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_thumb)
        ImageView imageView;

        @BindView(R.id.item_name)
        TextView name;

        @BindView(R.id.item_price)
        TextView price;

        public RelatedProductViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
