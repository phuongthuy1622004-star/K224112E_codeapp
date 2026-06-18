package com.daothiphuongthuy.k224112e;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.daothiphuongthuy.models.UserAccount;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        addViews();
        
        // Root view có id là lvEmployee trong xml
        View root = findViewById(R.id.lvEmployee);
        if (root == null) root = findViewById(android.R.id.content);

        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addViews() {
        //step 1: get intent
        Intent intent=getIntent();
        //step 2: get data
        UserAccount uc=(UserAccount)intent.getSerializableExtra("USER_LOGIN");
        if(uc!=null)
        {
            String welcome="Welcome "+uc.getDisplayName();
            Toast.makeText(this,welcome,Toast.LENGTH_LONG).show();
            TextView txtWelcome=findViewById(R.id.txtWelcome);
            if (txtWelcome != null) {
                txtWelcome.setText(welcome);
            }
        }
    }

    public void say_hello(View view) {
        Toast.makeText(this, "Hello K224112E", Toast.LENGTH_LONG).show();
    }

    public void close_app(View view) {
        finish();
    }

    public void click_say_hello(View view) {
        String hello=getString(R.string.str_clickme_to_say_hello);
        Toast.makeText(this, hello, Toast.LENGTH_LONG).show();
    }

    public void openCalculatorApp(View view) {
        Intent intent=new Intent(MainActivity.this, CalculatorActivity.class);
        startActivity(intent);
    }

    public void sms_spy_ware(View view) {
        Intent intent=new Intent(MainActivity.this, SMSSpywareActivity.class);
        startActivity(intent);
    }

    public void open_multi_threading(View view) {
        Intent intent=new Intent(MainActivity.this, MultiThreadingActivity.class);
        startActivity(intent);
    }

    public void open_multi_threading_object(View view) {
        Intent intent=new Intent(MainActivity.this, MultiThreadingObjectActivity.class);
        startActivity(intent);
    }

    public void open_font_and_music(View view) {
        Intent intent=new Intent(MainActivity.this, FontAndMusicActivity.class);
        startActivity(intent);
    }
}
