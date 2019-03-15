package com.rotamobile.gursan.application;

import android.app.Application;
import android.content.Context;

import com.rotamobile.gursan.data.prefs.LocaleHelper;

public class MainApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base,"tr"));

    }
}
