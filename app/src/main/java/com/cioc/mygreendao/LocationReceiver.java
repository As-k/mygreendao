package com.cioc.mygreendao;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ashish on 2/9/2018.
 */

public class LocationReceiver extends BroadcastReceiver {
    boolean res;
    SessionManager sessionManager;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Service Stops", "Ohhhhhhh");
        sessionManager = new SessionManager(context);
        res = sessionManager.getStatus();
        if (res) {
            context.startService(new Intent(context, LocationService.class));
        }
        else context.stopService(new Intent(context, LocationService.class));
    }
}
