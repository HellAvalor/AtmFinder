package com.andreykaraman.atmfinderukraine.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.andreykaraman.atmfinderukraine.model.Category;

import java.util.List;

/**
 * Created by Andrew on 13.08.2014.
 */
public class CategoryAdapter extends BaseAdapter implements SpinnerAdapter {

    private Activity activity;
    private List<Category> categories;

    public CategoryAdapter(Activity activity, List<Category> categories) {
        this.activity = activity;
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Category getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return categories.get(position).get_id();
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
        t1.setText(categories.get(position).getCategoryName());
        return spinView;
    }
}
