package com.qulbs.prayertimesazan;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.gson.Gson;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.qulbs.prayertimesazan.model.db.SholatWajibDB;
import com.qulbs.prayertimesazan.model.db.SinkronisasiSholatWajibDB;
import com.qulbs.prayertimesazan.model.db.kategoriSholatDB;
import com.qulbs.prayertimesazan.model.db.lokasiSholatDB;
import com.qulbs.prayertimesazan.model.kategoriSholat;
import com.qulbs.prayertimesazan.model.sholatWajib;
import com.qulbs.prayertimesazan.network.Api;
import com.qulbs.prayertimesazan.util.BaseAppCompatActivity;
import com.qulbs.prayertimesazan.util.Constant;
import com.qulbs.prayertimesazan.util.MyApplication;
import com.qulbs.prayertimesazan.util.Util;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.HttpUrl;
import okhttp3.Response;

public class PengaturanWaktuActivity extends BaseAppCompatActivity {
    private Toolbar toolbar;
    private AdapterPengaturanWaktuSholat mAdapterNotif;
    private CompositeDisposable disposable = new CompositeDisposable();
    private Location location;
    TextView tv_sinkron_subttitle;
    private TimePickerDialogCustom timePickerDialog;

    @Override
    protected int getLayoutResource() {
        return 0;
    }

    @Override
    protected boolean isTitleEnabled() {
        return false;
    }

    @Override
    protected String getTag() {
        return null;
    }

    @Override
    protected void onCreateActivity(Bundle pSavedInstanceState) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getCurrentLocation();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan_waktu);
        toolbar = findViewById(R.id.toolbar);
        RelativeLayout rlly_sinkron = findViewById(R.id.rlly_sinkron);
        tv_sinkron_subttitle = findViewById(R.id.tv_sinkron_subttitle);
        TextView tv_sinkron_waktu = findViewById(R.id.tv_sinkron_waktu);
        ListView lsvw_data = findViewById(R.id.lsvw_data);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAdapterNotif = new AdapterPengaturanWaktuSholat(com.qulbs.prayertimesazan.PengaturanWaktuActivity.this);
        lsvw_data.setAdapter(mAdapterNotif);

        List<SinkronisasiSholatWajibDB> wajibDBS = SinkronisasiSholatWajibDB.listAll(SinkronisasiSholatWajibDB.class);
        loadDataSholatWajib();

        if (wajibDBS.size() > 0) {
            tv_sinkron_subttitle.setText(Util.getTimeAgo(Util.dateToMillis(wajibDBS.get(0).getTanggal(), Constant.default_simpledateFormat),
                    System.currentTimeMillis()));

//            tv_sinkron_subttitle.setText(Util.getTimeAgo2(Util.dateToMillisDATE(wajibDBS.get(0).getTanggal() , Constant.default_simpledateFormat),
//                    System.currentTimeMillis()));

        }


        rlly_sinkron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: " + Util.getClosedOne());
                ;

//                startService(new Intent(getBaseContext(), AlarmService.class));
//                Util.setReminder(getApplicationContext(), AlarmReceiver.class,14,13);

                //https://stackoverflow.com/questions/12785702/android-set-multiple-alarms

                doSinkron();

            }
        });

        lsvw_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String iduser = Util.getSharedPreferenceString(getApplication(), Constant.PREFS_IS_USER, "");

                if (lokasiSholatDB.find(lokasiSholatDB.class,"aktif='1' AND iduser=?",iduser).size()>0){
                    SholatWajibDB db = mAdapterNotif.getItem(position);
                    if (db.getWaktu()!=null&& !db.getWaktu().isEmpty()) {
                        if (db.getIswajib()!=null&&db.getIswajib().equals("0")){
                            dialogSholatSunah(db);
                        }else {
                            setAlarm(db);
                        }
                    }else {
                        //do
                        Toast.makeText(com.qulbs.prayertimesazan.PengaturanWaktuActivity.this, "Belum Mengatur waktu", Toast.LENGTH_SHORT).show();
                        showTimePicker(com.qulbs.prayertimesazan.PengaturanWaktuActivity.this,db);
                    }
                }else {
                    Toast.makeText(com.qulbs.prayertimesazan.PengaturanWaktuActivity.this, "tidak bisa melakukan pengaturan alarm ,tentukan lokasi untuk melanjutkan!, ", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        http://droidmentor.com/schedule-notifications-using-alarmmanager/
//        http://blog.teamtreehouse.com/scheduling-time-sensitive-tasks-in-android-with-alarmmanager
//        https://stackoverflow.com/questions/9334179/how-to-tell-if-the-java-util-date-object-contains-the-time-portion
//    find the closest time between two tables in spark
//        How to find the closest time value to a given time value in matlab

    }

    public void setAlarm(SholatWajibDB db){
        AlertDialog.Builder builder = new AlertDialog.Builder(com.qulbs.prayertimesazan.PengaturanWaktuActivity.this);
        if (db.getAktif().equals("1")) {
            builder.setMessage("MenonAktifkan Alarm " + db.getNama() + " ?");
            builder.setPositiveButton("YA", (dialogInterface, i) -> {
                db.setAktif("0").save();
                loadDataSholatWajib();
                CheckAlarm();
            });
        } else {
            builder.setMessage("Mengaktifkan Alarm " + db.getNama() + " ?");
            builder.setPositiveButton("YA", (dialogInterface, i) -> {
                db.setAktif("1").save();
                loadDataSholatWajib();
                CheckAlarm();
            });
        }

        builder.setNegativeButton("TIDAK", (dialogInterface, i) -> {
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void dialogSholatSunah(SholatWajibDB db) {
        AlertDialog.Builder builder = new AlertDialog.Builder(com.qulbs.prayertimesazan.PengaturanWaktuActivity.this  , R.style.Theme_Dialog_Margin_4);
        List<String> where = new ArrayList<String>();

            where.add("Edit Waktu");
            where.add("MengAktif/Non Waktu");


        String[] strings = new String[where.size()];
        where.toArray(strings);

        builder.setItems(strings, (dialog, which) -> {
            switch (which) {
                case 0:
                    showTimePicker(com.qulbs.prayertimesazan.PengaturanWaktuActivity.this,db);
                    break;
                case 1:
                    setAlarm(db);
                    break;

                default:

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog.getWindow().getAttributes());
        lp.width = Util.toDIP(com.qulbs.prayertimesazan.PengaturanWaktuActivity.this, 360);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        alertDialog.getWindow().setAttributes(lp);
    }


    private void showTimePicker(Context mContext, SholatWajibDB wajibDB) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialogCustom(mContext, AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {


            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
               List<kategoriSholatDB> dbs= kategoriSholatDB.find(kategoriSholatDB.class,"idkategori=?",wajibDB.getIdkategori());
                if (dbs.size()>0){
                   String start=  dbs.get(0).getJamstart();
                   String end=  dbs.get(0).getJamend();

                   List<SholatWajibDB> dbsstart=SholatWajibDB.find(SholatWajibDB.class,"nama=?",start);
                   List<SholatWajibDB> dbsend=SholatWajibDB.find(SholatWajibDB.class,"nama=?",end);

                   if (dbsstart.size()>0&&dbsend.size()>0){
                   String[]  stringsstart = (dbsstart.get(0).getWaktu()).toString().split(":");
                   String[]  stringsend = (dbsend.get(0).getWaktu()).toString().split(":");

                       if (Util.dateToMillis(hourOfDay + ":" + minute, "HH:mm") < Util.dateToMillis(dbsstart.get(0).getWaktu(), "HH:mm")||
                               Util.dateToMillis(hourOfDay + ":" + minute, "HH:mm") > Util.dateToMillis(dbsend.get(0).getWaktu(), "HH:mm")
                               ) {
                           wajibDB.setWaktu(("")).save();
                           loadDataSholatWajib();
                           Toast.makeText(com.qulbs.prayertimesazan.PengaturanWaktuActivity.this, "Waktu harus diantara "+"("+dbsstart.get(0).getWaktu()+"-"+dbsend.get(0).getWaktu()+")", Toast.LENGTH_SHORT).show();

                       } else {
                           Log.d(TAG, "onTimeSet: waktu benar");
                           wajibDB.setWaktu(
                                   (String.valueOf(hourOfDay).length() == 1 ? "0" + String.valueOf(hourOfDay) : String.valueOf(hourOfDay)) + ":" +
                                           (String.valueOf(minute).length() == 1 ? "0" + String.valueOf(minute) : String.valueOf(minute))
                           ).save();
                           loadDataSholatWajib();
                           if (wajibDB.getAktif().equals("1")) CheckAlarm();
                           Toast.makeText(com.qulbs.prayertimesazan.PengaturanWaktuActivity.this, "Waktu Telah Tersimpan", Toast.LENGTH_SHORT).show();
                       }

                   }


                }
            }
        }, hour, minute, DateFormat.is24HourFormat(this));


        List<kategoriSholatDB> dbs= kategoriSholatDB.find(kategoriSholatDB.class,"idkategori=?",wajibDB.getIdkategori());
        if (dbs.size()>0) {
            String start = dbs.get(0).getJamstart();
            String end = dbs.get(0).getJamend();

            List<SholatWajibDB> dbsstart = SholatWajibDB.find(SholatWajibDB.class, "nama=?", start);
            List<SholatWajibDB> dbsend = SholatWajibDB.find(SholatWajibDB.class, "nama=?", end);
            if (dbsstart.size()>0&&dbsend.size()>0){
                timePickerDialog.setTitle(wajibDB.getNama() + "("+dbsstart.get(0).getWaktu()+"-"+dbsend.get(0).getWaktu()+")");
            }
        }


        timePickerDialog.show();
    }


    public void loadDataSholatWajib() {
        List<SholatWajibDB> sholatWajibDBS = SholatWajibDB.find(SholatWajibDB.class, "iswajib='1' or iswajib='0'");



        if (sholatWajibDBS.size() > 0) {
            mAdapterNotif.setList(sholatWajibDBS);
        } else {
            mAdapterNotif.setList(new ArrayList<>());

        }

    }


    private void doSinkron() {
        new AsyncTask<Void, Void, Boolean>() {

            ProgressDialog dialog = new ProgressDialog(com.qulbs.prayertimesazan.PengaturanWaktuActivity.this);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog.setTitle("Loading...");
                dialog.show();

            }

            @Override
            protected Boolean doInBackground(Void... voids) {
                boolean b = false;


                if (getApiSholat()) {

                    if (getKategoriSholat()) {

                        b = true;
                    }


                } else {
                    com.qulbs.prayertimesazan.PengaturanWaktuActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(com.qulbs.prayertimesazan.PengaturanWaktuActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

                return b;
            }

            @Override
            protected void onPostExecute(Boolean aVoid) {
                super.onPostExecute(aVoid);


                if (dialog.isShowing()) dialog.dismiss();


                List<SinkronisasiSholatWajibDB> wajibDBS = SinkronisasiSholatWajibDB.listAll(SinkronisasiSholatWajibDB.class);
                loadDataSholatWajib();

                if (wajibDBS.size() > 0) {
                    tv_sinkron_subttitle.setText(Util.getTimeAgo(Util.dateToMillis(wajibDBS.get(0).getTanggal(), Constant.default_simpledateFormat),
                            System.currentTimeMillis()));

                }

//

            }
        }.execute();

    }

    public boolean getApiSholat() {
        HttpUrl.Builder httpUrlBuilder = new HttpUrl.Builder();
        httpUrlBuilder.addQueryParameter("lat", location != null && location.getLatitude() != 0.0 ? String.valueOf(location.getLatitude()) : Constant.default_latitude)
                .addQueryParameter("lng", location != null && location.getLongitude() != 0.0 ? String.valueOf(location.getLongitude()) : Constant.default_longitude);

        try (Response response = new Api(com.qulbs.prayertimesazan.PengaturanWaktuActivity.this, true).
                get("", httpUrlBuilder)
        ) {
            if (response == null || !response.isSuccessful())
                throw new IOException("Unexpected code = " + response);

            String responseBodyString = response.body().string();
            JSONObject responseBodyObject = new JSONObject(responseBodyString);
            Gson gson = new Gson();
            sholatWajib sholatWajib = gson.fromJson(responseBodyString, sholatWajib.class);
            if (sholatWajib.getStatus().equalsIgnoreCase("ok")) {

                SinkronisasiSholatWajibDB.deleteAll(SinkronisasiSholatWajibDB.class);


                String iduser = Util.getSharedPreferenceString(getApplication(), Constant.PREFS_IS_USER, "");

                new SinkronisasiSholatWajibDB(sholatWajib).setTanggal(Util.getCurrentDate(Constant.default_simpledateFormat)).save();

                List<SholatWajibDB> wajibDBS = SholatWajibDB.find(SholatWajibDB.class, "iduser=?", iduser);


                if (wajibDBS.size() > 0) {
                    for (SholatWajibDB db : wajibDBS) {
                        switch (db.getNama()) {
                            case (Constant.name_sholat_subuh):
                                db.setWaktu(sholatWajib.getData().getFajr());
                                db.setIduser(iduser);
                                db.setAktif(db.getAktif());
                                db.save();
                                break;
                            case (Constant.name_time_sunrise):
                                db.setWaktu(sholatWajib.getData().getSunrise());
                                db.setIduser(iduser);
                                db.setAktif(db.getAktif());
                                db.save();
                                break;
                            case (Constant.name_sholat_dhuhur):
                                db.setWaktu(sholatWajib.getData().getDhuhr());
                                db.setIduser(iduser);
                                db.setAktif(db.getAktif());
                                db.save();
                                break;
                            case (Constant.name_sholat_ashar):
                                db.setWaktu(sholatWajib.getData().getAsr());
                                db.setIduser(iduser);
                                db.setAktif(db.getAktif());
                                db.save();
                                break;
                            case (Constant.name_time_sunset):
                                db.setWaktu(sholatWajib.getData().getSunset());
                                db.setIduser(iduser);
                                db.setAktif(db.getAktif());
                                db.save();
                                break;
                            case (Constant.name_sholat_maghrib):
                                db.setWaktu(sholatWajib.getData().getMaghrib());
                                db.setIduser(iduser);
                                db.setAktif(db.getAktif());
                                db.save();
                                break;
                            case (Constant.name_sholat_isha):
                                db.setWaktu(sholatWajib.getData().getIsha());
                                db.setIduser(iduser);
                                db.setAktif(db.getAktif());
                                db.save();
                                break;
                            case (Constant.name_time_sepertigamalam):
                                db.setWaktu(sholatWajib.getData().getSepertigaMalam());
                                db.setIduser(iduser);
                                db.setAktif(db.getAktif());
                                db.save();
                                break;
                            case (Constant.name_time_tengahmalam):
                                db.setWaktu(sholatWajib.getData().getTengahMalam());
                                db.setIduser(iduser);
                                db.setAktif(db.getAktif());
                                db.save();
                                break;
                            case (Constant.name_time_duapertigamalam):
                                db.setWaktu(sholatWajib.getData().getDuapertigaMalam());
                                db.setIduser(iduser);
                                db.setAktif(db.getAktif());
                                db.save();
                                break;
                        }
                    }
                } else   {
                    SholatWajibDB.deleteAll(SholatWajibDB.class);
                    new SholatWajibDB().setNama(Constant.name_sholat_subuh).setWaktu(sholatWajib.getData().getFajr()).setIduser(iduser).setIswajib("1").save();
                    new SholatWajibDB().setNama(Constant.name_time_sunrise).setWaktu(sholatWajib.getData().getSunrise()).setIduser(iduser).save();
                    new SholatWajibDB().setNama(Constant.name_sholat_dhuhur).setWaktu(sholatWajib.getData().getDhuhr()).setIduser(iduser).setIswajib("1").save();
                    new SholatWajibDB().setNama(Constant.name_sholat_ashar).setWaktu(sholatWajib.getData().getAsr()).setIduser(iduser).setIswajib("1").save();
                    new SholatWajibDB().setNama(Constant.name_time_sunset).setWaktu(sholatWajib.getData().getSunset()).setIduser(iduser).save();
                    new SholatWajibDB().setNama(Constant.name_sholat_maghrib).setWaktu(sholatWajib.getData().getMaghrib()).setIduser(iduser).setIswajib("1").save();
                    new SholatWajibDB().setNama(Constant.name_sholat_isha).setWaktu(sholatWajib.getData().getIsha()).setIduser(iduser).setIswajib("1").save();
                    new SholatWajibDB().setNama(Constant.name_time_sepertigamalam).setWaktu(sholatWajib.getData().getSepertigaMalam()).setIduser(iduser).save();
                    new SholatWajibDB().setNama(Constant.name_time_tengahmalam).setWaktu(sholatWajib.getData().getTengahMalam()).setIduser(iduser).save();
                    new SholatWajibDB().setNama(Constant.name_time_duapertigamalam).setWaktu(sholatWajib.getData().getDuapertigaMalam()).setIduser(iduser).save();
                }

                return true;
            }
        } catch (ConnectException e) {
            Toast.makeText(com.qulbs.prayertimesazan.PengaturanWaktuActivity.this, "Kesalahan koneksi, Silahkan coba beberapa saat lagi dan pastikan terdapat koneksi internet!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }

    public boolean getKategoriSholat() {
        HttpUrl.Builder httpUrlBuilder = new HttpUrl.Builder();

        try (Response response = new Api(com.qulbs.prayertimesazan.PengaturanWaktuActivity.this).
                get("index.php/kategori_sholat", httpUrlBuilder)
        ) {
            if (response == null || !response.isSuccessful())
                throw new IOException("Unexpected code = " + response);

            String responseBodyString = response.body().string();
            Gson gson = new Gson();
            JSONObject responseBodyObject = new JSONObject(responseBodyString);

            if (responseBodyObject.getBoolean("status")) {

                kategoriSholat kategoriSholat = gson.fromJson(responseBodyString, kategoriSholat.class);
                kategoriSholatDB.deleteAll(kategoriSholatDB.class);

                List<kategoriSholatDB> dbs = new ArrayList<>();
                for (int i = 0; i < kategoriSholat.getData().size(); i++) {
                    dbs.add(new kategoriSholatDB(kategoriSholat.getData().get(i)));

                    List<SholatWajibDB> dbs2 = SholatWajibDB.find(SholatWajibDB.class, "nama=?", kategoriSholat.getData().get(i).getNama());
                    if (dbs2.size() > 0) {
                        dbs2.get(0).setIdkategori(kategoriSholat.getData().get(i).getId()).save();
                    }


                }
                kategoriSholatDB.saveInTx(dbs);


                // cek and insert sholat sunah

                String iduser = Util.getSharedPreferenceString(getApplication(), Constant.PREFS_IS_USER, "");

                List<kategoriSholatDB> dbs1 = kategoriSholatDB.find(kategoriSholatDB.class, "iswajib=?", "0");
                List<SholatWajibDB> dbs2 = SholatWajibDB.find(SholatWajibDB.class, "iswajib=?", "0");

                SholatWajibDB.deleteInTx(dbs2);

                for (int i = 0; i < dbs1.size(); i++) {
                    new SholatWajibDB().setNama(dbs1.get(i).getNama())
                            .setWaktu("")
                            .setIduser(iduser)
                            .setIdkategori(dbs1.get(i).getIdkategori())
                            .setIswajib("0").save();

                }


                return true;
            }
        } catch (ConnectException e) {
            Toast.makeText(com.qulbs.prayertimesazan.PengaturanWaktuActivity.this, "Kesalahan koneksi, Silahkan coba beberapa saat lagi dan pastikan terdapat koneksi internet!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }

    private void getCurrentLocation() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        // I can access the location now
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        disposable.add(MyApplication.getInstance().getRxLocation().location().lastLocation()
                                .subscribe(location -> {
                                    this.location = location;
                                    Log.d(TAG, "getCurrentLocation: " + location.getLatitude() + "," + location.getLongitude());
                                    disposable.dispose();

                                }, e -> {
                                    Log.d(TAG, "getCurrentLocation: e" + e.getStackTrace());

                                }, () -> {

                                }));
                    } else {
                        Log.d(TAG, "getCurrentLocation: access denied");
                    }
                });
    }


    private void CheckAlarm(){
        String iduser=  Util.getSharedPreferenceString(getApplication(),Constant.PREFS_IS_USER,"");
        List<SholatWajibDB> sholatWajibDBS= SholatWajibDB.find(SholatWajibDB.class,"idkategori!='' ");
        List<lokasiSholatDB> lokasiSholatDBS= lokasiSholatDB.find(lokasiSholatDB.class,"aktif='1' AND iduser=? ",
                iduser);

        String lokasi=lokasiSholatDBS.size()>0?lokasiSholatDBS.get(0).getNama():"";
        String koordinat=lokasiSholatDBS.size()>0?lokasiSholatDBS.get(0).getLatlong():"";


        if (sholatWajibDBS.size()>0){
            for (int i = 0; i < sholatWajibDBS.size(); i++) {
                String[] separated = sholatWajibDBS.get(i).getWaktu().equals("")?"00:00".split(":"):
                        sholatWajibDBS.get(i).getWaktu()
                                .split(":");
                DateTime dt1 = new DateTime();
                MutableDateTime mdt1 = new MutableDateTime(dt1); // mdt1 = dt1
                mdt1.setHourOfDay(Util.parseInteger(separated[0]));
                mdt1.setMinuteOfHour(Util.parseInteger(separated[1]));
                mdt1.setSecondOfMinute(0);
                mdt1.setMillisOfSecond(0);



                if(mdt1.isBeforeNow()){
                    mdt1.addDays(1);
                }


                if (sholatWajibDBS.get(i).getAktif().equals("1")&&
                        !sholatWajibDBS.get(i).getWaktu().equals("")){
                    Log.d(TAG, "onClick: jam set aktif "+mdt1.toString()+"id"+sholatWajibDBS.get(i).getIdkategori());
                    Util.AddAlarm(getApplication(),Util.parseInteger(sholatWajibDBS.get(i).getIdkategori()),mdt1,Constant.DAILY, iduser,sholatWajibDBS.get(i).getWaktu(),
                            sholatWajibDBS.get(i).getNama(),lokasi,koordinat,false);
                }else {
                    Log.d(TAG, "onClick: jam set aktif di non "+mdt1.toString()+"id"+sholatWajibDBS.get(i).getIdkategori());
                    Util.AddAlarm(getApplication(),Util.parseInteger(sholatWajibDBS.get(i).getIdkategori()),mdt1,Constant.DAILY,iduser,sholatWajibDBS.get(i).
                                    getWaktu(),sholatWajibDBS.get(i).getNama(),lokasi,koordinat
                            ,true);
                }
            }

        }
    }
}
