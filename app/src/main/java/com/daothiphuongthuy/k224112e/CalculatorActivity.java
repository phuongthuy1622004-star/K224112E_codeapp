package com.daothiphuongthuy.k224112e;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class CalculatorActivity extends AppCompatActivity {

    EditText edtformula;
    TextView txtmc, txtmr, txtmplus, txtmminus, txtms, txtm;
    Button btndel, btncalculate;
    String formular_share_pref="formular"; //thêm

    double memory = 0; // biến lưu bộ nhớ

    View.OnClickListener m_onclick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator);
        addView();
        addEvent();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addEvent() {
        btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get current data;
                String current_data=edtformula.getText().toString();
                //remove last character:
                String new_value ="";
                if(current_data.length()>1)
                {
                    new_value=current_data.substring(0,current_data.length()-1);
                }
                //set new value:
                edtformula.setText(new_value);
            }
        });
        btncalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //step 1: get data (formular)
                String formular = edtformula.getText().toString();
                //step 2: invoke library for formular (find internet)..
                String result="";
                try {
                    Expression expression = new ExpressionBuilder(formular).build();
                    double evalResult = expression.evaluate();
                    // Nếu kết quả là số nguyên thì hiện không có .0
                    if (evalResult == (long) evalResult) {
                        result = String.valueOf((long) evalResult);
                    } else {
                        result = String.valueOf(evalResult);
                    }
                } catch (Exception e) {
                    result = "Lỗi"; // Công thức sai cú pháp
                }
                //step 3:
                edtformula.setText(result);
            }
        });

        m_onclick=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy giá trị hiện tại trên màn hình
                double currentValue = 0;
                try {
                    String text = edtformula.getText().toString();
                    if (!text.isEmpty() && !text.equals("Lỗi")) {
                        currentValue = Double.parseDouble(text);
                    }
                } catch (NumberFormatException e) {
                    // Bỏ qua nếu đang là công thức chưa tính
                }

                if(view.equals(txtm))
                {
                    //khách hàng nhấn txtM
                    Toast.makeText(CalculatorActivity.this,
                            "Bộ nhớ: " + memory, Toast.LENGTH_SHORT).show();
                }
                else if (view.equals(txtmminus))
                {
                    //khách hàng nhấn txtMinus
                    memory -= currentValue;
                    updateMemoryDisplay();
                }
                else if (view.equals(txtmc))
                {
                    // MC: Xóa bộ nhớ
                    memory = 0;
                    txtm.setText("M");
                }
                else if (view.equals(txtmr))
                {
                    // MR: Đưa giá trị bộ nhớ ra màn hình
                    if (memory == (long) memory)
                        edtformula.setText(String.valueOf((long) memory));
                    else
                        edtformula.setText(String.valueOf(memory));
                }
                else if (view.equals(txtmplus))
                {
                    // M+: Cộng vào bộ nhớ
                    memory += currentValue;
                    updateMemoryDisplay();
                }
                else if (view.equals(txtms))
                {
                    // MS: Lưu giá trị hiện tại vào bộ nhớ
                    memory = currentValue;
                    updateMemoryDisplay();
                }
                //không dùng dấu == để so sánh vì nó không hiểu so sánh ô nhớ khi dùng ==
            }
        };
        //m_onclick là biến có khả năng sinh sự kiện (variable as listener)
        //thường dùng để sharing sự kiện (từ 2 view trở lên)
        txtmc.setOnClickListener(m_onclick);
        txtmr.setOnClickListener(m_onclick);
        txtmplus.setOnClickListener(m_onclick);
        txtmminus.setOnClickListener(m_onclick);
        txtms.setOnClickListener(m_onclick);
        txtm.setOnClickListener(m_onclick);
    }

    private void updateMemoryDisplay() {
        if (memory == (long) memory)
            txtm.setText("M=" + (long) memory);
        else
            txtm.setText("M=" + memory);
    }

    private void addView() {
        edtformula=findViewById(R.id.edtformula);

        btndel=findViewById(R.id.btndel);
        btncalculate=findViewById(R.id.btncalculate);

        txtmc=findViewById(R.id.txtmc);
        txtmr=findViewById(R.id.txtmr);
        txtmplus=findViewById(R.id.txtmplus);
        txtmminus=findViewById(R.id.txtmminus);
        txtms=findViewById(R.id.txtms);
        txtm=findViewById(R.id.txtm);
    }

    public void processInputData(View view) {
        Button btn_clicked= (Button) view;//// Nói rõ: "view này là Button" -> Giờ mới dùng được getText() của Button -> String input_value =btn_clicked.getText().toString();
        //old value:
        String old_value=edtformula.getText().toString();
        //input value:
        String input_value =btn_clicked.getText().toString();
        //new value (lasted value)
        String new_value=old_value+input_value;
        //show new value for customer;
        edtformula.setText(new_value);
    }
    //them
    @Override
    protected void onPause() {
        super.onPause();
        // Lưu công thức vào SharedPreferences
        String formular = edtformula.getText().toString();
        SharedPreferences preference = getSharedPreferences(formular_share_pref, MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString("formular", formular);
        editor.commit();
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Đọc công thức từ SharedPreferences
        SharedPreferences preference = getSharedPreferences(formular_share_pref, MODE_PRIVATE);
        String formular = preference.getString("formular", "");
        edtformula.setText(formular);
    }
}