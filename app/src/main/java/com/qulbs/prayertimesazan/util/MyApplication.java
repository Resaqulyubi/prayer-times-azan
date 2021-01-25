package com.qulbs.prayertimesazan.util;

import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import androidx.appcompat.app.AppCompatDelegate;

import com.birbit.android.jobqueue.JobManager;
import com.facebook.stetho.Stetho;
import com.orm.SugarApp;
import com.patloew.rxlocation.RxLocation;

import net.danlew.android.joda.JodaTimeAndroid;

import com.qulbs.prayertimesazan.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by WINDOWS 10 on 20/03/2017.
 */

public class MyApplication extends SugarApp {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private static MyApplication mInstance;
    private JobManager jobManager;
    private RxLocation rxLocation;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        Stetho.InitializerBuilder initializerBuilder =
                Stetho.newInitializerBuilder(this);
        initializerBuilder.enableWebKitInspector(
                Stetho.defaultInspectorModulesProvider(this)
        );
        initializerBuilder.enableDumpapp(
                Stetho.defaultDumperPluginsProvider(this)
        );
        Stetho.Initializer initializer = initializerBuilder.build();
        Stetho.initialize(initializer);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        JodaTimeAndroid.init(this);
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public RxLocation getRxLocation() {
        if (rxLocation == null) rxLocation = new RxLocation(this);
        return rxLocation;
    }





    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }
}