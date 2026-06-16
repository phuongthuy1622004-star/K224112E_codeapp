package com.daothiphuongthuy.k224112e;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.daothiphuongthuy.models.DataWarehouse;
import com.daothiphuongthuy.models.Product;

import java.util.ArrayList;

public class MultiThreadingObjectActivity extends AppCompatActivity {

    EditText edtNumberProduct;
    TextView txtPercent;
    ProgressBar progressBarPercent;
    ListView lvProduct;
    ArrayList<Product> products;
    ArrayAdapter<Product> adapterProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_multi_threading_object);
        addViews();
        addEvents();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addEvents() {
        lvProduct.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                products.remove(i);
                adapterProduct.notifyDataSetChanged();
                return false;
            }
        });
    }

    private void addViews() {
        // 1. Ánh xạ View từ XML sang biến Java
        edtNumberProduct = findViewById(R.id.edtNumberProduct);
        txtPercent = findViewById(R.id.txtPercent);         // ← đây là cách khai báo đúng
        progressBarPercent = findViewById(R.id.progressBarPercent);
        lvProduct = findViewById(R.id.lvProduct);

        // 2. Khởi tạo danh sách dữ liệu rỗng
        products = new ArrayList<>();

        // 3. Khởi tạo Adapter, gắn vào ListView
        adapterProduct = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                products
        );
        lvProduct.setAdapter(adapterProduct);
    }

    Handler mainThread = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            int percent = message.arg1;
            txtPercent.setText(percent + "%");
            progressBarPercent.setProgress(percent);
            if (message.obj != null) {
                Product p = (Product) message.obj;
                products.add(p);
                adapterProduct.notifyDataSetChanged();
            }
            if (percent == 100) {
                Toast.makeText(MultiThreadingObjectActivity.this, "Download Product finished", Toast.LENGTH_LONG).show();
            }
            return false;
        }
    });

    public void processDownloadProduct(View view) {
        int n = Integer.parseInt(edtNumberProduct.getText().toString());
        //thực hiện download sản phẩm thông qua tiểu trình (thread):
        Thread downloadProductThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < n; i++) {
                    Product p = DataWarehouse.downloadProduct(i);
                    int percent = (i + 1) * 100 / n;
                    if (p == null) {// coi như lỗi và dừng luôn tiến trình tải dữ liệu, có thể vẫn chưa chạy xong hoặc ta không dừng, nhưng bỏ qua 1 vòng lặp
                        break;//giả sử dừng luôn
                    }
                    //lấy message lấy MainThread:
                    Message message = mainThread.obtainMessage();
                    //truyền thông tin vào message
                    message.arg1 = percent;// tỉ lệ hoàn thành
                    message.obj = p;//product tải thành công ở vị trí i
                    //khi gọi hàm này thì handleMessage (@NonNull Message message) của MainThread sẽ nhận được Message
                    mainThread.sendMessage(message);
                    //sau đó giả sử cho tiểu trình
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //gửi thông báo kết thúc công việc
                Message finalMessage = mainThread.obtainMessage();
                finalMessage.arg1 = 100;
                mainThread.sendMessage(finalMessage);
            }
        });
        downloadProductThread.start();
    }
}