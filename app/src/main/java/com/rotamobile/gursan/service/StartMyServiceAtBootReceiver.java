package com.rotamobile.gursan.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.rotamobile.gursan.ui.splash.Splash;

public class StartMyServiceAtBootReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

/*        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent startApplication = new Intent(context, Main.class);
            startApplication.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(startApplication);
        }*/

        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // Set the alarm here.
            Intent startApplication = new Intent(context, Splash.class);
            startApplication.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(startApplication);
        }
    }
}
