package com.ramveltrader.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.ramveltrader.R;
import com.ramveltrader.data.network.models.CategoryResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NavItemDrawerAdapter extends RecyclerView.Adapter<NavItemDrawerAdapter.NavItemViewHolder> {

    private Context mContext;

    private List<CategoryResponse> mNavItems;

    public NavItemDrawerAdapter(Context context) {
        mContext = context;
    }

    public void refresh(List<CategoryResponse> navItems) {
        this.mNavItems = navItems;
        notifyDataSetChanged();
    }

    @Override
    public NavItemDrawerAdapter.NavItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_nav_drawer, parent, false);
        return new NavItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NavItemDrawerAdapter.NavItemViewHolder viewHolder, int position) {
       CategoryResponse navItem =  mNavItems.get(position);
       viewHolder.linearLayout.setTag(navItem);
       viewHolder.title.setText(navItem.getName());
    }

    @Override
    public int getItemCount() {
        return mNavItems != null ? mNavItems.size() : 0;
    }

    class NavItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.icon)
        ImageView iconView;

        @BindView(R.id.root)
        LinearLayout linearLayout;

        public NavItemViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
