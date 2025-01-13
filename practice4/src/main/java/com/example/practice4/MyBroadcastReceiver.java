package com.example.practice4;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private final static String LOG_TAG = "MyBroadcastReceiver_Airplane";

    public MyBroadcastReceiver(){}

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOG_TAG, "Action: " + intent.getAction() + " is changed");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Toast.makeText(context, "Action: " + intent.getAction()
                + "is changed", Toast.LENGTH_SHORT).show();
    }
}
