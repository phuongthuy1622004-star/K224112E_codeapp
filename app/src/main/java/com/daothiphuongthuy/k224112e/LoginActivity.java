package com.daothiphuongthuy.k224112e;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.daothiphuongthuy.models.ListUserAccount;
import com.daothiphuongthuy.models.UserAccount;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LoginActivity extends AppCompatActivity {

    public static final String DATABASE_NAME = "K234112ESales.sqlite";
    public static SQLiteDatabase database = null;

    private void copyDataBase() {
        try {
            File dbFile = getDatabasePath(DATABASE_NAME);
            if (!dbFile.exists()) {
                if (CopyDBFromAsset()) {
                    Toast.makeText(LoginActivity.this,
                            "Copy database successful!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Copy database fail!", Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            Log.e("Error: ", e.toString());
        }
    }

    private boolean CopyDBFromAsset() {
        try {
            InputStream inputStream = getAssets().open(DATABASE_NAME);
            File dbFile = getDatabasePath(DATABASE_NAME);
            File dbDir = dbFile.getParentFile();
            if (dbDir != null && !dbDir.exists()) {
                dbDir.mkdirs();
            }
            OutputStream outputStream = new FileOutputStream(dbFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            return true;
        } catch (IOException e) {
            Log.e("CopyDB", "Error copying database", e);
            return false;
        }
    }

    BroadcastReceiver internetStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //bất kỳ khi nào internet/mobile data change state
            //tự bay vào đay
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION))
            {
                //  bỏ trống vì đang chỉ lắng nghe wifi, nếu đồ án gk có thêm 5% pin bổ sung vào đây
            }
            Toast.makeText(LoginActivity.this, "internet/ mobile data changing state",
                    Toast.LENGTH_LONG).show();
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                btnLogin.setEnabled(true);
            }
            else
            {
                btnLogin.setEnabled(false);
            }
        }
    };
    EditText edtUserName;
    EditText edtPassword;
    TextView txtMessage;
    CheckBox chkSaveLogin;
    String name_share_pref = "LoginInfo";
    RadioButton radAdmin, radEmployee;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        addViews();
        copyDataBase();
        
        View root = findViewById(R.id.lvEmployee);
        if (root == null) root = findViewById(android.R.id.content);

        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void addViews() {
        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        txtMessage = findViewById(R.id.txtMessage);
        chkSaveLogin = findViewById(R.id.chkSaveLogin);
        radAdmin = findViewById(R.id.radAdmin);
        radEmployee = findViewById(R.id.radEmployee);
        btnLogin = findViewById(R.id.btnLogin);
    }

    public void LoginSystem(View view) {
        String username = edtUserName.getText().toString();
        String password = edtPassword.getText().toString();
        UserAccount uc = ListUserAccount.login(username, password);
        if (uc != null) {
            boolean saved = chkSaveLogin.isChecked();
            SharedPreferences preferences = getSharedPreferences(name_share_pref, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("username", username);
            editor.putString("password", password);
            editor.putBoolean("saved", saved);
            editor.apply();

            txtMessage.setText(getString(R.string.str_login_success));
            if (radAdmin.isChecked()) {
                //Intent intent = new Intent(LoginActivity.this, CategoryActivity.class);
                //Intent intent=new Intent(LoginActivity.this, OrderManagementActivity.class);
                //Intent intent=new Intent(LoginActivity.this, CategoryActivity.class);
                //Intent intent=new Intent(LoginActivity.this, MyContactActivity.class);

                //Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                Intent intent = new Intent(LoginActivity.this, EmployeeManagementActivity.class);


                intent.putExtra("USER_LOGIN", uc);
                startActivity(intent);
            } else {
                Intent intent = new Intent(LoginActivity.this, MyUELQueryActivity.class);
                startActivity(intent);
            }
        } else {
            txtMessage.setText(getString(R.string.str_login_failed));
        }
    }

    public void ExitSystem(View view) {
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
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preference = getSharedPreferences(name_share_pref, MODE_PRIVATE);
        String username = preference.getString("username", "");
        String password = preference.getString("password", "");
        boolean saved = preference.getBoolean("saved", false);
        if (saved) {
            edtUserName.setText(username);
            edtPassword.setText(password);
        }
        chkSaveLogin.setChecked(saved);

        IntentFilter internetFilter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION); //lăng nghe internet, pin, .. -> truyền nhiều đối số -> khi onResume ddunsg
        registerReceiver(internetStateReceiver,internetFilter);
    }
    protected void onPause (){
        super.onPause(); // onPause (dùng app zalo -> kh dùng được K23412App -> kh hđ -> gỡ bỏ -> đỡ tốn pin)
        unregisterReceiver(internetStateReceiver);
    }
}
