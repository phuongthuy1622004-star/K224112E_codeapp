package com.daothiphuongthuy.k224112e;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.daothiphuongthuy.models.Employee;

public class AddEmployeeActivity extends AppCompatActivity {

    EditText edtEmpId, edtEmpName, edtEmpPhone;
    AutoCompleteTextView actBirthplace;
    ArrayAdapter<String> adapterBirthplace;
    ImageView imgSave, imgCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_employee);
        
        addViews();
        addEvents();
        
        View root = findViewById(R.id.main);
        if (root == null) root = findViewById(android.R.id.content);
        
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    private void addEvents() {
        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processAddNewEmployee();
            }
        });
        
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void processAddNewEmployee() {
        String id = edtEmpId.getText().toString();
        String name = edtEmpName.getText().toString();
        String phone = edtEmpPhone.getText().toString();
        String birthplace = actBirthplace.getText().toString();

        Employee emp = new Employee(id, name, phone, birthplace);
        //step 1: get intent
        Intent intent = getIntent();
        //step 2: set data
        intent.putExtra("NEW_EMPLOYEE", emp);
        //step 3: set result
        setResult(888, intent);
        //step 4: finish activity
        finish();
    }

    private void addViews() {
        edtEmpId = findViewById(R.id.edtEmpId);
        edtEmpName = findViewById(R.id.edtEmpName);
        edtEmpPhone = findViewById(R.id.edtEmpPhone);
        actBirthplace = findViewById(R.id.actBirthplace);

        adapterBirthplace = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        String[] arr_birthplace = getResources().getStringArray(R.array.list_birthplace);
        adapterBirthplace.addAll(arr_birthplace);
        actBirthplace.setAdapter(adapterBirthplace);

        imgSave   = findViewById(R.id.imgSave);
        imgCancel = findViewById(R.id.imgCancel);
    }
}
