package com.example.singh.broadcastreceiver;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editText, editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.etBroadcastReceiver);
        editText2 = (EditText) findViewById(R.id.etSendData);

    }

    public void sendBroadcastUsingManifest(View view) {

        Intent intent = new Intent("myAction");
        sendBroadcast(intent);

    }

    public void sendBroadcastUsingActivity(View view) {

        Intent intent = new Intent("myNewAction");
        intent.putExtra("myString", editText.getText().toString());
        sendBroadcast(intent);


    }


    MyNewReceiver dinamicReceiver = new MyNewReceiver();

    @Override
    protected void onStart() {
        super.onStart();

        registerReceiver(dinamicReceiver, new IntentFilter("myNewAction"));

    }

    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(dinamicReceiver);
    }

    public void sendBroadcastToOtherApp(View view) {

        Intent intent = new Intent("sendingToOtherApp");
        intent.putExtra("newString", editText2.getText().toString());
        sendBroadcast(intent);

    }


    public class MyNewReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            String data = intent.getStringExtra("myString");
            if (data == null) {
                data = "";
            }
            Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
            System.out.println("TOKEN MAGICO I AM VALIDATING MyNewReceiver <" + data + ">");

        }
    }
}
