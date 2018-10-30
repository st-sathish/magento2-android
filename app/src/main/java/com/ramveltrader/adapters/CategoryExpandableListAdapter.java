package com.ramveltrader.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.ramveltrader.R;
import com.ramveltrader.data.network.models.CategoryResponse;

import java.util.ArrayList;
import java.util.List;

public class CategoryExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;

    private List<CategoryResponse> mExpandableList = new ArrayList<>();

    public CategoryExpandableListAdapter(Context context) {
        this.mContext = context;
    }

    public void refresh(List<CategoryResponse> expandableList) {
        this.mExpandableList = expandableList;
        notifyDataSetChanged();
    }

    @Override
    public CategoryResponse getChild(int listPosition, int expandedListPosition) {
        return this.mExpandableList.get(listPosition).getChildrenData().get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final CategoryResponse categoryResponse = getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_category, null);
        }
        TextView expandedListTextView = convertView.findViewById(R.id.item_title);
        expandedListTextView.setText(categoryResponse.getName());
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.mExpandableList.get(listPosition).getChildrenData().size();
    }

    @Override
    public CategoryResponse getGroup(int listPosition) {
        return this.mExpandableList.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.mExpandableList.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        CategoryResponse categoryResponse = getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_category_group, null);
        }
        TextView listTitleTextView = convertView.findViewById(R.id.item_group_title);
        listTitleTextView.setText(categoryResponse.getName());
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
