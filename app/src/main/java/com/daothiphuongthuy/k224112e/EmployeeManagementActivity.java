package com.daothiphuongthuy.k224112e;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class EmployeeManagementActivity extends AppCompatActivity {

    Button btnExit;

    //xong dataset -> adapter -> listview
    ListView lvEmployee;
    ArrayList<String>listEmployee;
    ArrayAdapter<String>adapterEmployee;
    //
    EditText edtId, edtName, edtPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employee_management);
        addViews();
        addEvents();

        loadData();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.lvEmployee), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void loadData() {
        listEmployee.add("e1-Tèo-123456789");
        listEmployee.add("e2-Bi-123456789");
        listEmployee.add("e3-Bo-123456789");
        listEmployee.add("e4-Tủn-123456789");
        listEmployee.add("e5-Na-123456789");
        //nói Adapter cập nhật giao dện
        adapterEmployee.notifyDataSetChanged();
    }

    private void addEvents() {
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proccessExit();
            }
        });

        lvEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                displayEmployeeInfor(i);
            }
        });
    }

    private void displayEmployeeInfor(int i) {
        String data = listEmployee.get(i);
        String[] items = data.split("-");
        //hiển thị items [0] -> id, item[1] -> name, item[2] -> phone    }
        edtId.setText(items[0]);
        edtName.setText(items[1]);
        edtPhone.setText(items[2]);

    }

    private void proccessExit() {
        Dialog custom = new Dialog(this);
        custom.setContentView(R.layout.custome_dialog);

        // Tìm view trong Dialog, không phải Activity
        ImageView imgSave = custom.findViewById(R.id.imgYes);
        ImageView imgCancel = custom.findViewById(R.id.imgCancel);

        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Thoát Activity
            }
        });

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                custom.dismiss(); // Đóng Dialog
            }
        });

        custom.show(); // Đừng quên hiển thị dialog!
    }

    private void addViews() {
        btnExit=findViewById(R.id.btnExit);
        //xong dataset -> adapter -> listview
        lvEmployee=findViewById(R.id.lvEmployee);
        listEmployee=new ArrayList<>();
        adapterEmployee=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listEmployee);
        lvEmployee.setAdapter(adapterEmployee);
        //

        edtId=findViewById(R.id.edtId);
        edtName=findViewById(R.id.edtName);
        edtPhone=findViewById(R.id.edtEmpPhone);
    }


    public void saveEmployee(View view) {
        // 1. Lấy dữ liệu từ các EditText
        String id = edtId.getText().toString().trim();
        String name = edtName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();

        // 2. Kiểm tra dữ liệu rỗng (Validation)
        if (id.isEmpty()) {
            edtId.setError("Vui lòng nhập ID");
            edtId.requestFocus();
            return;
        }

        // Chuỗi dữ liệu mới theo format: id-name-phone
        String newData = id + "-" + name + "-" + phone;
        int updateIndex = -1;

        // 3. Kiểm tra xem ID đã tồn tại trong list hay chưa
        for (int i = 0; i < listEmployee.size(); i++) {
            String currentItem = listEmployee.get(i);
            // Tách lấy ID của phần tử hiện tại để so sánh
            String currentId = currentItem.split("-")[0];

            if (currentId.equalsIgnoreCase(id)) {
                updateIndex = i; // Lưu lại vị trí nếu tìm thấy ID trùng
                break;
            }
        }

        // 4. Thực hiện Thêm mới hoặc Cập nhật
        if (updateIndex != -1) {
            // Nếu đã tồn tại -> Cập nhật phần tử tại vị trí đã tìm thấy
            listEmployee.set(updateIndex, newData);
        } else {
            // Nếu chưa tồn tại -> Thêm mới vào cuối danh sách
            listEmployee.add(newData);
        }

        // 5. Cập nhật giao diện thông qua Adapter
        adapterEmployee.notifyDataSetChanged();

        // 6. Xóa trắng form để chuẩn bị cho lần nhập tiếp theo
        clearForm();
    }

    private void clearForm() {
        edtId.setText("");
        edtName.setText("");
        edtPhone.setText("");
        edtId.requestFocus(); // Đưa con trỏ về ô ID
    }

    public void removeEmployee(View view) {
    }
}