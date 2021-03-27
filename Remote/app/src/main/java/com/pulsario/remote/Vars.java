package com.pulsario.remote;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.Toast;

public class Vars {
    public static final String IP_ADDRESS = "IP_ADDRESS";
    public static final String MyPref = "MyPref";
    public static final int REQUEST_CHANGE_IP = 333;

    public static SharedPreferences sharedPreferences;

    public static void showMsg(Activity activity, String msg){
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }
}
