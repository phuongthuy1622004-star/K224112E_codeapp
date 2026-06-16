package com.daothiphuongthuy.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.daothiphuongthuy.k224112e.R;
import com.daothiphuongthuy.models.Category;

public class CategoryAdapter extends ArrayAdapter<Category> {
    Activity context;
    int resource;

    public CategoryAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View custom = inflater.inflate(resource, null);

        Category c = getItem(position);
        
        if (c != null) {
            TextView txtCategoryId = custom.findViewById(R.id.txtCategoryId);
            TextView txtCategoryName = custom.findViewById(R.id.txtCategoryName);
            TextView txtCategoryDesc = custom.findViewById(R.id.txtCategoryDesc);

            // Sửa lại đúng tên getter trong model Category
            txtCategoryId.setText(c.getCatId());
            txtCategoryName.setText(c.getCatName());
            txtCategoryDesc.setText(c.getCatDes());
        }

        return custom;
    }
}
