package com.ramveltrader.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramveltrader.R;
import com.ramveltrader.data.network.models.CartResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartDetailListAdapter extends RecyclerView.Adapter<CartDetailListAdapter.CartDetailViewHolder> {
    private List<CartResponse> mProductDetails = new ArrayList<>();

    private Context mContext;

    private int mLayout;

    private OnCartProductListener mOnCartProductListener;

    public CartDetailListAdapter(Context context, int layout, OnCartProductListener mOnCartProductListener) {
        mContext = context;
        this.mLayout = layout;
        this.mOnCartProductListener = mOnCartProductListener;
    }


    public CartResponse getItem(int position) {
        return mProductDetails.get(position);
    }

    @Override
    public CartDetailListAdapter.CartDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(mLayout, parent, false);
        return new CartDetailListAdapter.CartDetailViewHolder(view);
    }

    public void update(List<CartResponse> productDetails) {
        mProductDetails.addAll(productDetails);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(CartDetailListAdapter.CartDetailViewHolder itemHolder, int position) {
        CartResponse productDetail = mProductDetails.get(position);
        itemHolder.name.setText(productDetail.getName());
        String price = mContext.getResources().getString(R.string.Rs)+" " +String.valueOf(productDetail.getPrice());
        itemHolder.price.setText(price);
        itemHolder.quantity.setText(productDetail.getQty().toString());
        //ProductImageUtils.loadImage(mContext, itemHolder.imageView, "dummy");
    }

    @Override
    public int getItemCount() {
        return mProductDetails != null ? mProductDetails.size() : 0;
    }

    public void remove(int position) {
        mProductDetails.remove(position);
        notifyDataSetChanged();
    }

    public interface OnCartProductListener {
        void removedCartItem(View v, int position);
        void updateCartItem(String quantity, int position);
    }

    class CartDetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        @BindView(R.id.item_thumb)
        ImageView imageView;

        @BindView(R.id.item_name)
        TextView name;

        @BindView(R.id.item_price)
        TextView price;

        @BindView(R.id.item_quantity)
        EditText quantity;

        @BindView(R.id.item_remove_cart_product)
        Button itemRemoveCartProduct;

        @BindView(R.id.item_update_cart_product)
        Button itemUpdateCartProduct;


        public CartDetailViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(this);
            itemRemoveCartProduct.setOnClickListener(this);
            itemUpdateCartProduct.setOnClickListener(this);
            v.setTag(this); //By calling setTag, all buttons can use the same listener
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.item_remove_cart_product :
                    mOnCartProductListener.removedCartItem(view, getAdapterPosition());
                    break;
                case R.id.item_update_cart_product :
                    mOnCartProductListener.updateCartItem(quantity.getText().toString(), getAdapterPosition());
            }
        }
    }
}

