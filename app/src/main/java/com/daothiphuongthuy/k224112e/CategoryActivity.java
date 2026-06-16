package com.daothiphuongthuy.k224112e;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.daothiphuongthuy.adapter.CategoryAdapter;
import com.daothiphuongthuy.dals.CategoryDAO;
import com.daothiphuongthuy.models.Category;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    ListView lvCategory;
    ArrayList<Category> categories;
    CategoryAdapter adapterCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);
        addViews();
        addEvents();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addEvents() {
        lvCategory.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView adapterView, View view, int i, long l) {
                processRemoveCategory(i);
                return false;
            }
        });
    }

    private void processRemoveCategory(int i) {
        Category category = categories.get(i);
        long result = CategoryDAO.removeCategory(this, category);
        if (result > 0) {
            //B1: truy vấn lại dữ liệu mới nhất
            categories = CategoryDAO.getCategories(this);
            //B2: Xóa dữ liệu cũ trên adapter hiện tại và thêm dữ liệu mới
            adapterCategory.clear();
            //B3: Thêm mới lại dữ liệu mới hoàn toàn:
            adapterCategory.addAll(categories);
            //B4: Thông báo cho Adapter để cập nhật lại giao diện
            adapterCategory.notifyDataSetChanged();
        }
    }

    private void addViews() {
        lvCategory = findViewById(R.id.lvCategory);
        categories = CategoryDAO.getCategories(this);
        adapterCategory = new CategoryAdapter(this, R.layout.category_custom_item);
        adapterCategory.addAll(categories);
        lvCategory.setAdapter(adapterCategory);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.category_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.mnu_category_new)
        {
            //open Category new activity
            Intent intent=new Intent(this, CategoryNewActivity.class);
            startActivityForResult(intent,1);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == 2) {
            // TODO: xử lý khi save thành công (reload list,...)
        }
        else if (requestCode == 1 && resultCode == 3) {
            //B1: truy vấn lại dữ liệu mới nhất
            categories = CategoryDAO.getCategories(this);
            //B2: Xóa dữ liệu cũ trên adapter hiện tại và thêm dữ liệu mới
            adapterCategory.clear();
            //B3: Thêm mới lại dữ liệu mới hoàn toàn:
            adapterCategory.addAll(categories);
            //B4: Thông báo cho Adapter để cập nhật lại giao diện
            adapterCategory.notifyDataSetChanged();
        }
    }
}