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
import com.daothiphuongthuy.models.Order;
import com.daothiphuongthuy.models.DataWarehouse;

public class OrderAdapter extends ArrayAdapter<Order> {
    Activity context;
    int resource;

    public OrderAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View custom = inflater.inflate(resource, null);
        Order order = getItem(position);

        if (order != null) {
            TextView txtOrderId = custom.findViewById(R.id.txtOrderId);
            TextView txtOrderDate = custom.findViewById(R.id.txtOrderDate);
            TextView txtOrderStatus = custom.findViewById(R.id.txtOrderStatus);
            TextView txtOrderTotal = custom.findViewById(R.id.txtOrderTotal);

            txtOrderId.setText(order.getOrderId());
            txtOrderDate.setText(order.getOrderDate().toString());
            
            if (order.getOrderStatus() != null) {
                txtOrderStatus.setText(order.getOrderStatus().getDescription());
            } else {
                txtOrderStatus.setText("N/A");
            }
            
            txtOrderTotal.setText(String.valueOf(DataWarehouse.sumOfMoney(order)));
        }

        return custom;
    }
}
