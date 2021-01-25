package com.qulbs.prayertimesazan.util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.facebook.stetho.Stetho;

/**
 * Created by FERIYAL on 11/3/2016.
 */

public abstract class BaseActivity extends Activity {

    public static final String TAG = "BaseActivity";


    private Context context;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

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

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        switch (pItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return false;
    }

    private void onCreateLayout(int p_resource, boolean p_title) {
        try {
            if (!p_title) {
                requestWindowFeature(Window.FEATURE_NO_TITLE);
            } else {
                getActionBar().setDisplayHomeAsUpEnabled(true);
            }
            setContentView(p_resource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
    }

}
