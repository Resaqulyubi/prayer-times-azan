package com.qulbs.prayertimesazan.util;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.facebook.stetho.Stetho;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by FERIYAL on 11/3/2016.
 */

public abstract class BaseAppCompatActivity extends AppCompatActivity {

    public static final String TAG = "BaseActivity";
    public static AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private Context context;


    public static boolean hasSoftKeys(Context context, WindowManager windowManager) {
        boolean hasSoftwareKeys = true;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display d = windowManager.getDefaultDisplay();

            DisplayMetrics realDisplayMetrics = new DisplayMetrics();
            d.getRealMetrics(realDisplayMetrics);

            int realHeight = realDisplayMetrics.heightPixels;
            int realWidth = realDisplayMetrics.widthPixels;

            DisplayMetrics displayMetrics = new DisplayMetrics();
            d.getMetrics(displayMetrics);

            int displayHeight = displayMetrics.heightPixels;
            int displayWidth = displayMetrics.widthPixels;

            hasSoftwareKeys = (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
        } else {
            boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
            boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            hasSoftwareKeys = !hasMenuKey && !hasBackKey;
        }
        return hasSoftwareKeys;
    }

    protected abstract int getLayoutResource();

    protected abstract boolean isTitleEnabled();

    protected abstract String getTag();

    protected abstract void onCreateActivity(Bundle pSavedInstanceState);

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
                finish();
                break;
        }
        return false;
    }

    private void onCreateLayout(int p_resource, boolean p_title) {
        try {
            if (!p_title) {
                getSupportActionBar().hide();
            } else {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//                getSupportActionBar().setHomeAsUpIndicator(R.drawable.header_drawer_back);
            }
            setContentView(p_resource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public final int toDIP(float p_value) {
        return (int) ((p_value * getApplicationContext().getResources().getDisplayMetrics().density) + 0.5f);
    }
}
