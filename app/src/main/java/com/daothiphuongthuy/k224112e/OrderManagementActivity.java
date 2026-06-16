package com.daothiphuongthuy.k224112e;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.daothiphuongthuy.adapter.OrderAdapter;
import com.daothiphuongthuy.models.DataWarehouse;
import com.daothiphuongthuy.models.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class OrderManagementActivity extends AppCompatActivity {

    TextView txtFromDate;
    TextView txtToDate;
    ImageView imgFromDate;
    ImageView imgToDate;
    ImageView imgClearFilter;
    ImageView imgFilter;
    ListView lvOrder;
    ArrayList<Order> orders;
    //ArrayAdapter<Order> orderAdapter;
    OrderAdapter orderAdapter;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Calendar calFromDate = Calendar.getInstance();
    Calendar calToDate = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener dateFromListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            calFromDate.set(Calendar.YEAR, i);
            calFromDate.set(Calendar.MONTH, i1);
            calFromDate.set(Calendar.DAY_OF_MONTH, i2);
            txtFromDate.setText(sdf.format((calFromDate.getTime())));
        }
    };

    DatePickerDialog.OnDateSetListener dateToListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            calToDate.set(Calendar.YEAR, i);
            calToDate.set(Calendar.MONTH, i1);
            calToDate.set(Calendar.DAY_OF_MONTH, i2);
            txtToDate.setText(sdf.format((calToDate.getTime())));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_management);
        addViews();
        addEvents();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addEvents() {
        imgFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectFromDate();
            }
        });

        imgToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectToDate();
            }
        });

        imgClearFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orders = DataWarehouse.getOrders();
                orderAdapter.clear();
                orderAdapter.addAll(orders);
                orderAdapter.notifyDataSetChanged();
            }
        });
        imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date fromDate = calFromDate.getTime();
                Date toDate = calToDate.getTime();
                orders = DataWarehouse.filterOrdersByDate(fromDate, toDate);
                orderAdapter.clear();
                orderAdapter.addAll(orders);
                orderAdapter.notifyDataSetChanged();
            }
        });
    }

    private void selectFromDate() {
        DatePickerDialog picker = new DatePickerDialog(this,
                dateFromListener,
                calFromDate.get(Calendar.YEAR),
                calFromDate.get(Calendar.MONTH),
                calFromDate.get(Calendar.DAY_OF_MONTH));
        picker.show();
    }

    private void selectToDate() {
        DatePickerDialog picker = new DatePickerDialog(this,
                dateToListener,
                calToDate.get(Calendar.YEAR),
                calToDate.get(Calendar.MONTH),
                calToDate.get(Calendar.DAY_OF_MONTH));
        picker.show();
    }

    private void addViews() {
        txtFromDate = findViewById(R.id.txtFromDate);
        txtToDate = findViewById(R.id.txtToDate);
        imgFromDate = findViewById(R.id.imgFromDate);
        imgToDate = findViewById(R.id.imgToDate);
        imgClearFilter = findViewById(R.id.imgClearFilter);
        imgFilter = findViewById(R.id.imgFilter);

        lvOrder = findViewById(R.id.lvOrder);
        orders = DataWarehouse.getOrders();
        //orderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, orders);
        orderAdapter = new OrderAdapter(this, R.layout.order_customer_item);
        orderAdapter.addAll(orders);
        lvOrder.setAdapter(orderAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.order_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        orderAdapter.clear();

        if (item.getItemId() == R.id.mnu_all_status_order) {
            orders = DataWarehouse.filterOrderByStatus(com.daothiphuongthuy.models.OrderStatus.ALL);
        } else if (item.getItemId() == R.id.mnu_completed_order) {
            orders = DataWarehouse.filterOrderByStatus(com.daothiphuongthuy.models.OrderStatus.COMPLETED);
        } else if (item.getItemId() == R.id.mnu_customer_complain) {
            orders = DataWarehouse.filterOrderByStatus(com.daothiphuongthuy.models.OrderStatus.CUSTOMER_COMPLAIN);
        } else if (item.getItemId() == R.id.mnu_going_logistic) {
            orders = DataWarehouse.filterOrderByStatus(com.daothiphuongthuy.models.OrderStatus.GOING_LOGISTIC);
        } else if (item.getItemId() == R.id.mnu_notyet_payment) {
            orders = DataWarehouse.filterOrderByStatus(com.daothiphuongthuy.models.OrderStatus.NOT_YET_PAYMENT);
        }

        orderAdapter.addAll(orders);
        return super.onOptionsItemSelected(item);
    }
}
