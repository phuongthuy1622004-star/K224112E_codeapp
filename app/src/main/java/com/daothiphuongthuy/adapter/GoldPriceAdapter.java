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
import com.daothiphuongthuy.models.GoldPrice;

public class GoldPriceAdapter extends ArrayAdapter<GoldPrice> {
    Activity context;
    int resource;

    public GoldPriceAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View custom = convertView;
        if (custom == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            custom = inflater.inflate(resource, parent, false);
        }

        GoldPrice gold = getItem(position);
        if (gold != null) {
            TextView txtDate = custom.findViewById(R.id.txtDate);
            TextView txtBuy = custom.findViewById(R.id.txtBuy);
            TextView txtSell = custom.findViewById(R.id.txtSell);

            txtDate.setText(gold.getDate());
            txtBuy.setText("Mua: " + gold.getBuy());
            txtSell.setText("Bán: " + gold.getSell());
        }

        return custom;
    }
}
