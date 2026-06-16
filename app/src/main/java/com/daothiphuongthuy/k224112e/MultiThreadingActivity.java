package com.daothiphuongthuy.k224112e;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MultiThreadingActivity extends AppCompatActivity {

    EditText edtButton;
    TextView txtPercent;
    ProgressBar progressBarPercent;
    LinearLayout linearLayoutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_multi_threading);
        addViews();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void addViews() {
        edtButton = findViewById(R.id.edtButton);
        txtPercent = findViewById(R.id.txtPercent);
        progressBarPercent = findViewById(R.id.progressBarPercent);
        linearLayoutButton = findViewById(R.id.linearLayoutButton);
    }
    // tiến trình chính (có nhều biến) => progress bar => khi chạy 1 thread tiểu trình khác không được chạm vào khác biến tiến trình chính
    //Main Thread (tiến trình chính cho màn hình hiện tại)
    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            //tiểu trình sẽ gửi thông điệp về cho Main Thread thông qua message
            int percent=message.arg1;
            int value=message.arg2;
            txtPercent.setText(percent+"%");
            progressBarPercent.setProgress(percent);
            Button button=new Button(MultiThreadingActivity.this);
            button.setWidth(300);
            button.setHeight(50);
            button.setText(value+"");
            linearLayoutButton.addView(button);
            return false;
        }
    });
    public void processLongTimeTask(View view) {
        int n=Integer.parseInt(edtButton.getText().toString());
        //tạo tiểu trình để chạy long time task:
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                // không được phép truy suất tới bất kỳ biến view (control) nào trong đây vì trong này là chạy long time task (dạng background). Muốn truy suất giao diện để cập nhật Visualization thì gửi thông điệp qua cho MainThread "thầy Thanh làm cho hết nhưng gửi le ĐHQG để show)
                Random random=new Random();
                for(int i=1;i<=n;i++) //vòng lặp làm nhiều task của MainThread
                {
                    //tạo giá trị cho đối tượng
                    int value= random.nextInt(100);
                    //lấy địa chỉ thông điệp của MainThread
                    Message message=handler.obtainMessage();
                    //gán giá trị mình tạo ra cho thông đó đó:
                    message.arg1=i/n*100;//percent
                    message.arg2=value;//value
                    //message.obj nếu muốn truyền đối tượng phức tạp thì dùng obj
                    //gửi thông điệp trở lại cho Mainthread để xử lý Visualization
                    handler.sendMessage(message);
                    try {
                        //cần phải sleep để tránh quá tải CPU
                        //cũng như cho các tiểu trình khác xen vào lúc nghỉ để xử lý
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }
}