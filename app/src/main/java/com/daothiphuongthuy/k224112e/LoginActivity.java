package com.daothiphuongthuy.k224112e;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    /*
    Declare all variables for interactive views
     */
    EditText edtUserName;
    EditText edtPassword;
    TextView txtMessage;
    CheckBox chkSaveLogin;
    String name_share_pref="LoginInfo"; //thêm
    RadioButton radAdmin, radEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        addViews();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addViews() {
        edtUserName=findViewById(R.id.edtUserName);
        edtPassword=findViewById(R.id.edtPassword);
        txtMessage=findViewById(R.id.txtMessage);
        chkSaveLogin=findViewById(R.id.chkSaveLogin);
        radAdmin=findViewById(R.id.radAdmin);
        radEmployee=findViewById(R.id.radEmployee);
    }

    public void LoginSystem (View view) {
        String username=edtUserName.getText().toString();
        String password=edtPassword.getText().toString();
        if(username.equalsIgnoreCase("admin") &&
        password.equals("123"))
        {
            boolean saved=chkSaveLogin.isChecked();
            SharedPreferences preference=getSharedPreferences(name_share_pref,MODE_PRIVATE);
            SharedPreferences.Editor editor=preference.edit();
            editor.putString("username",username);
            editor.putString("password",password);
            editor.putBoolean("saved",saved);
            editor.commit();

            txtMessage.setText(getString(R.string.str_login_success));
            if (radAdmin.isChecked()){
                Intent intent = new Intent(LoginActivity.this, MainActivity.class); // nhấn button nay thì 2 câu lệnh tiếp theo dùng để mở màn hình mới
                startActivity(intent);
            }
            else
            {
                Intent intent = new Intent(LoginActivity.this, EmployeeManagementActivity.class); // nhấn button nay thì 2 câu lệnh tiếp theo dùng để mở màn hình mới
                startActivity(intent);
            }

        }
        else
        {
            txtMessage.setText(getString(R.string.str_login_failed));
        }
    }

    public void exitsystem(View view) {
//        finish();
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle(getString(R.string.str_confirm_exit));
                builder.setMessage(getString(R.string.str_out));
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setPositiveButton(getString(R.string.str_co), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                builder.setNegativeButton(getString(R.string.str_khong), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog dialog=builder.create();
                dialog.setCanceledOnTouchOutside(false);//có chỉnh thì nhấn ở ngoài không -> false, true thanh toán thì không
                dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preference=getSharedPreferences(name_share_pref,MODE_PRIVATE);
        String username=preference.getString("username","");
        String password=preference.getString("password","");
        boolean saved=preference.getBoolean("saved",false);
        if(saved)
        {
            edtUserName.setText(username);
            edtPassword.setText(password);
        }
        chkSaveLogin.setChecked(saved);
    }
}

