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
    String formular_share_pref="formular";

    double memory = 0;

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
                String current_data = edtformula.getText().toString();
                String new_value = "";
                if (current_data.length() > 1) {
                    new_value = current_data.substring(0, current_data.length() - 1);
                }
                edtformula.setText(new_value);
            }
        });

        btncalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //step 1: get data
                String formular = edtformula.getText().toString();

                //step 1.5: thay ký hiệu toán học → ký hiệu lập trình
                formular = formular.replace(":", "/"); // thêm dòng này
                formular = formular.replace("×", "*");
                formular = formular.replace(",", ".");

                //step 2: tính toán
                String result = "";
                try {
                    Expression expression = new ExpressionBuilder(formular).build();
                    double evalResult = expression.evaluate();
                    if (evalResult == (long) evalResult) {
                        result = String.valueOf((long) evalResult);
                    } else {
                        result = String.valueOf(evalResult);
                    }
                } catch (Exception e) {
                    result = "Lỗi";
                }

                //step 3: hiện kết quả
                edtformula.setText(result);
            }
        });

        m_onclick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double currentValue = 0;
                try {
                    String text = edtformula.getText().toString();
                    if (!text.isEmpty() && !text.equals("Lỗi")) {
                        currentValue = Double.parseDouble(text);
                    }
                } catch (NumberFormatException e) {
                    // Bỏ qua nếu đang là công thức chưa tính
                }

                if (view.equals(txtm)) {
                    Toast.makeText(CalculatorActivity.this,
                            "Bộ nhớ: " + memory, Toast.LENGTH_SHORT).show();
                } else if (view.equals(txtmminus)) {
                    memory -= currentValue;
                    updateMemoryDisplay();
                } else if (view.equals(txtmc)) {
                    memory = 0;
                    txtm.setText("M");
                } else if (view.equals(txtmr)) {
                    if (memory == (long) memory)
                        edtformula.setText(String.valueOf((long) memory));
                    else
                        edtformula.setText(String.valueOf(memory));
                } else if (view.equals(txtmplus)) {
                    memory += currentValue;
                    updateMemoryDisplay();
                } else if (view.equals(txtms)) {
                    memory = currentValue;
                    updateMemoryDisplay();
                }
            }
        };

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
        edtformula = findViewById(R.id.edtformula);
        btndel = findViewById(R.id.btndel);
        btncalculate = findViewById(R.id.btncalculate);
        txtmc = findViewById(R.id.txtmc);
        txtmr = findViewById(R.id.txtmr);
        txtmplus = findViewById(R.id.txtmplus);
        txtmminus = findViewById(R.id.txtmminus);
        txtms = findViewById(R.id.txtms);
        txtm = findViewById(R.id.txtm);
    }

    public void processInputData(View view) {
        Button btn_clicked = (Button) view;
        String old_value = edtformula.getText().toString();
        String input_value = btn_clicked.getText().toString();
        String new_value = old_value + input_value;
        edtformula.setText(new_value);
    }

    @Override
    protected void onPause() {
        super.onPause();
        String formular = edtformula.getText().toString();
        SharedPreferences preference = getSharedPreferences(formular_share_pref, MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString("formular", formular);
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preference = getSharedPreferences(formular_share_pref, MODE_PRIVATE);
        String formular = preference.getString("formular", "");
        edtformula.setText(formular);
    }
}