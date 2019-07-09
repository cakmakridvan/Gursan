package com.rotamobile.gursan.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.rotamobile.gursan.data.prefs.LocaleHelper;
import com.rotamobile.gursan.service.FireBaseService;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base,"tr"));

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().name("data_gursan.realm").deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(configuration);
    }
}
