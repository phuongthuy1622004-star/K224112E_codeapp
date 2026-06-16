package com.daothiphuongthuy.k224112e;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.daothiphuongthuy.adapter.EmployeeAdapter;
import com.daothiphuongthuy.models.Department;
import com.daothiphuongthuy.models.Employee;

import java.util.ArrayList;

public class EmployeeAdvanced extends AppCompatActivity {

    ListView lvEmployee;
    ArrayList<Employee>listOfEmployee;
    EmployeeAdapter adapterEmployee;
    Spinner spDepartment;
    ArrayList<Department> listDepartment;
    ArrayAdapter<Department> adapterDepartment;
    Employee selectedEmployee = null;

    ImageView imgAddEmployee, imgEditEmployee, imgDeleteEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employee_advanced);
        addViews();
        sampleData();
        addEvents();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.lvEmployee), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addEvents() {
        spDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    // Hiển thị toàn bộ nhân viên từ tất cả phòng ban
                    ArrayList<Employee> allEmployees = new ArrayList<>();
                    for (int j = 1; j < listDepartment.size(); j++) {
                        allEmployees.addAll(listDepartment.get(j).getListOfEmployee());
                    }
                    adapterEmployee.clear();
                    adapterEmployee.addAll(allEmployees);
                } else {
                    // Hiển thị nhân viên của phòng ban được chọn
                    Department selectedDepartment = listDepartment.get(i);
                    adapterEmployee.clear();
                    adapterEmployee.addAll(selectedDepartment.getListOfEmployee());
                }
                adapterEmployee.notifyDataSetChanged();
                selectedEmployee = null; // Reset selection khi đổi phòng ban
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        lvEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedEmployee = adapterEmployee.getItem(i);
            }
        });

        imgAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAdd = new Intent(EmployeeAdvanced.this, AddEmployeeActivity.class);

                //startActivity(intentAdd);
                startActivityForResult(intentAdd,999);
            }
        });
    }

    public void removeEmployee(View view) {
        if (selectedEmployee == null) {
            Toast.makeText(this, "Vui lòng chọn nhân viên cần xóa!", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");
        builder.setIcon(android.R.drawable.ic_delete);
        builder.setMessage("Bạn có chắc chắn muốn xóa nhân viên: " + selectedEmployee.getName() + "?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Xóa khỏi adapter
                adapterEmployee.remove(selectedEmployee);

                // Xóa khỏi danh sách dữ liệu gốc của các phòng ban để đồng bộ
                for (Department d : listDepartment) {
                    d.getListOfEmployee().remove(selectedEmployee);
                }

                adapterEmployee.notifyDataSetChanged();
                selectedEmployee = null; // Xóa xong thì bỏ chọn
                Toast.makeText(EmployeeAdvanced.this, "Đã xóa thành công", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Không", null);
        builder.create().show();
    }

    private void sampleData() {
        Department d0 = new Department("-1", "----ALL----");
        Department d1 = new Department("01", "Phòng hành chính");
        Department d2 = new Department("02", "Phòng kế toán");
        Department d3 = new Department("03", "Phòng marketing");
        Department d4 = new Department("04", "Phòng sản xuất");
        listDepartment.add(d0);
        listDepartment.add(d1);
        listDepartment.add(d2);
        listDepartment.add(d3);
        listDepartment.add(d4);

        // d1 - Phòng hành chính
        d1.addEmployee(new Employee("e1", "tèo", "12345"));

        // d2 - Phòng kế toán
        ArrayList<Employee> list1 = new ArrayList<>();
        list1.add(new Employee("e2", "bi", "12345"));
        list1.add(new Employee("e3", "bo", "12345"));
        list1.add(new Employee("e4", "bỏ", "12345"));
        d2.addListEmployee(list1);

        // d4 - Phòng sản xuất
        ArrayList<Employee> list2 = new ArrayList<>();
        list2.add(new Employee("e5", "tí", "12345"));
        list2.add(new Employee("e6", "tèo", "12345"));
        list2.add(new Employee("e7", "tủn", "12345"));
        list2.add(new Employee("e8", "tún", "12345"));
        d4.addListEmployee(list2);
        adapterDepartment.notifyDataSetChanged();
    }

    private void addViews() {
        lvEmployee = findViewById(R.id.lvEmployee);
        listOfEmployee = new ArrayList<>();
        adapterEmployee = new EmployeeAdapter(this, R.layout.item_custom_employee);
        listOfEmployee.add(new Employee("1", "A", "123"));
        listOfEmployee.add(new Employee("2", "B", "123"));
        listOfEmployee.add(new Employee("3", "C", "123"));
        listOfEmployee.add(new Employee("4", "D", "123"));
        listOfEmployee.add(new Employee("5", "E", "123"));
        adapterEmployee.addAll(listOfEmployee);
        lvEmployee.setAdapter(adapterEmployee);
        adapterEmployee.notifyDataSetChanged();

        spDepartment = findViewById(R.id.spDepartment);
        listDepartment = new ArrayList<>();
        adapterDepartment = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listDepartment);
        adapterDepartment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDepartment.setAdapter(adapterDepartment);

        imgAddEmployee = findViewById(R.id.imgAddEmployee);
        imgEditEmployee = findViewById(R.id.imgEditEmployee);
        imgDeleteEmployee = findViewById(R.id.imgDeleteEmployee);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==999 && resultCode==888)
        {
            Employee emp=(Employee)data.getSerializableExtra("NEW_EMPLOYEE");
            Department pHuman=listDepartment.get(2);
            pHuman.addEmployee(emp);
            adapterEmployee.clear();
            adapterEmployee.addAll(pHuman.getListOfEmployee());
            adapterEmployee.notifyDataSetChanged();
        }
    }
}