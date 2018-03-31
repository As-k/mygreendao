package com.cioc.mygreendao;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ashish on 2/14/2018.
 */

public class SessionManager {
    Context context;

    SharedPreferences sp;
    SharedPreferences.Editor spe;
    private String STATUS = "status";

    public SessionManager(Context context) {
        this.context = context;
        sp = context.getSharedPreferences("registered_status", context.MODE_PRIVATE);
        spe = sp.edit();
    }

    public boolean getStatus() {
        return sp.getBoolean(STATUS, false);
    }

    public void setStatus(boolean status) {
        spe.putBoolean(STATUS, status);
        spe.commit();
    }
}
