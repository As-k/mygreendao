package com.cioc.mygreendao;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.PersistableBundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    public static Switch sw;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sw = findViewById(R.id.sw);
        sessionManager = new SessionManager(this);
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    startService(new Intent(HomeActivity.this,LocationService.class));
                    sessionManager.setStatus(isChecked);
                } else {
                    stopService(new Intent(HomeActivity.this,LocationService.class));
                    sessionManager.setStatus(isChecked);
                }
            }
        });

        if (savedInstanceState != null) {
            boolean b = savedInstanceState.getBoolean("switch");
            if (b) {
                startService(new Intent(HomeActivity.this,LocationService.class));
            } else {
                stopService(new Intent(HomeActivity.this,LocationService.class));
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Register for the particular broadcast based on ACTION string
        IntentFilter filter = new IntentFilter(LocationService.ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(testReceiver, filter);
        // or `registerReceiver(testReceiver, filter)` for a normal broadcast
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the listener when the application is paused
        LocalBroadcastManager.getInstance(this).unregisterReceiver(testReceiver);
        // or `unregisterReceiver(testReceiver)` for a normal broadcast
    }

    // Define the callback for what to do when data is received
    private BroadcastReceiver testReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int resultCode = intent.getIntExtra("resultCode", RESULT_CANCELED);
            if (resultCode == RESULT_OK) {
                String resultValue = intent.getStringExtra("resultValue");
                Toast.makeText(HomeActivity.this, resultValue, Toast.LENGTH_SHORT).show();
                intent = new Intent("com.android.techtrainner");
                intent.putExtra("yourvalue", "torestore");
                sendBroadcast(intent);
            }
        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putBoolean("switch",sw.isChecked());
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.w("onDestroy", "------------------------------------------ Destroyed Notification Service");
        Intent intent = new Intent("com.android.techtrainner");
        intent.putExtra("yourvalue", "torestore");
        sendBroadcast(intent);
    }
}
