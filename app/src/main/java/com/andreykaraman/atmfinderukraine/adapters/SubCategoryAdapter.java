package com.andreykaraman.atmfinderukraine.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.andreykaraman.atmfinderukraine.model.SubCategory;

import java.util.List;

/**
 * Created by Andrew on 13.08.2014.
 */
public class SubCategoryAdapter extends BaseAdapter implements SpinnerAdapter {

    private Activity activity;
    private List<SubCategory> subCategories;

    public SubCategoryAdapter(Activity activity, List<SubCategory> subCategories) {
        this.activity = activity;
        this.subCategories = subCategories;
    }

    @Override
    public int getCount() {
        return subCategories.size();
    }

    @Override
    public SubCategory getItem(int position) {
        return subCategories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return subCategories.get(position).get_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View spinView;
        if (convertView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            spinView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, null);
        } else {
            spinView = convertView;
        }
        TextView t1 = (TextView) spinView.findViewById(android.R.id.text1);
        t1.setText(subCategories.get(position).getSubCategoryName());
        return spinView;
    }
}
