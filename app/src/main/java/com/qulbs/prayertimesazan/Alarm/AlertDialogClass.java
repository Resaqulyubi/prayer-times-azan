package com.qulbs.prayertimesazan.Alarm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import com.qulbs.prayertimesazan.R;
import com.qulbs.prayertimesazan.model.db.detailSholatDB;
import com.qulbs.prayertimesazan.model.detailSholat;
import com.qulbs.prayertimesazan.network.Api;
import com.qulbs.prayertimesazan.util.Constant;
import com.qulbs.prayertimesazan.util.Util;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.qulbs.prayertimesazan.util.BaseActivity.TAG;

public class AlertDialogClass extends Activity {
    AlertDialog.Builder mAlertDlgBuilder;
    AlertDialog mAlertDialog;
    View mDialogView = null;
    Button mOKBtn, mCancelBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//https://enoent.fr/blog/2014/04/06/android-display-a-dialog-from-an-appwidget/
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        //setContentView(R.layout.activity_main);
       Context context = new ContextThemeWrapper(AlertDialogClass.this, R.style.Theme_Dialog_Fullscreen);

      String id=  getIntent().getStringExtra(Constant.RECORD_ID);
      String jam=  getIntent().getStringExtra("jam");
      String iduser=  getIntent().getStringExtra("iduser");
      String nama=  getIntent().getStringExtra("nama");
      String lokasinama=  getIntent().getStringExtra("lokasinama");
      String lokasilatlong=  getIntent().getStringExtra("lokasilatlong");
      boolean isClosed=  getIntent().getBooleanExtra("isClosed",false);

        // For the Holo one on 3.0+ devices, fallback on 1.x/2.x devices:
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            context = new ContextThemeWrapper(AlertDialogClass.this, android.R.style.Theme_Holo);
        } else {
            context = new ContextThemeWrapper(AlertDialogClass.this, android.R.style.Theme_Dialog);
        }

        if (isClosed){
            Toast.makeText(context, "Anda Telah Berada dilokasi, Selamat Beribadah ", Toast.LENGTH_LONG).show();
            send(jam,iduser,id,lokasinama,lokasilatlong);
        }else {
            new AlertDialog.Builder(context)
                    .setTitle("Pengingat Sholat")
                    .setMessage("Waktu sholat "+nama)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            send(jam,iduser,id,lokasinama,lokasilatlong);
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Handle a negative answer
                        }
                    })
                    .setIcon(R.drawable.ic_alarm_on_indigo_a200_24dp)
                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            finish();
                        }
                    })
                    .show();
        }


    }


    void send(String jam, String iduser, String id, String lokasinama, String lokasilatlong){
        // Handle a positive answer
        FormBody.Builder formBody = new FormBody.Builder()
                .add("iduser", iduser)
                .add("idkategori", id)
                .add("latlong", lokasilatlong)
                .add("nama_lokasi", lokasinama)
                .add("jam", jam)
                .add("createdate", Util.getCurrentDate(Constant.default_simpledateFormat))
                .add("action", "POST");

        new Api(getApplicationContext()).post("index.php/sholat_record", formBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code = " + response);

                    String responseBodyString = responseBody.string();
                    JSONObject responseBodyObject = new JSONObject(responseBodyString);
                    Gson gson=new Gson();
                    detailSholat detailSholat = gson.fromJson(responseBodyString, detailSholat.class);
                    if (detailSholat.getStatus().equals("true")&&detailSholat.getData().size()>0) {
                        new detailSholatDB(detailSholat.getData().get(0)).save();
                        Log.d(TAG, "onResponse: success");
                    }else {

                    }
                } catch (IOException e) {
                    e.printStackTrace();

                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        });

    }

    View.OnClickListener mDialogbuttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.ID_Ok)
            {
                mAlertDialog.dismiss();
                finish();
            }
            else if(v.getId() == R.id.ID_Cancel)
            {
                mAlertDialog.dismiss();
                finish();
            }
        }
    };
}