package com.daothiphuongthuy.k224112e;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Date;

public class SMSSpywareActivity extends AppCompatActivity {

    TextView txtPhone;
    TextView txtTime;
    TextView txtMessage;

    BroadcastReceiver smsReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] arrMessages = (Object[]) bundle.get("pdus");
                for(int i = 0; i<arrMessages.length; i++){
                    byte[] bytes = (byte[]) arrMessages[i];
                    SmsMessage message = SmsMessage.createFromPdu(bytes);
                    String phone = message.getDisplayOriginatingAddress();
                    String body = message.getDisplayMessageBody();
                    long time = message.getTimestampMillis();
                    Date date = new Date(message.getTimestampMillis());
                    txtPhone.setText(phone);
                    txtTime.setText(date.toString());
                    txtMessage.setText(body);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_smsspyware);
        addViews();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addViews() {
        txtPhone=findViewById(R.id.txtPhone);
        txtTime=findViewById(R.id.txtTime);
        txtMessage=findViewById(R.id.txtMessage);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter smsFilter=new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(smsReceiver,smsFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(smsReceiver);
    }
}