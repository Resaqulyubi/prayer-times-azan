package com.qulbs.prayertimesazan.Alarm;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.qulbs.prayertimesazan.MainActivity;
import com.qulbs.prayertimesazan.OnNewLocationListener;
import com.qulbs.prayertimesazan.util.Constant;
import com.qulbs.prayertimesazan.util.Util;

import static com.qulbs.prayertimesazan.util.Constant.PREFS_IS_latlong;

public class AlarmReceiver extends BroadcastReceiver {

//    https://github.com/codepath/android_guides/issues/220
//    http://devdeeds.com/android-location-tracking-in-background-service/

    String TAG = "AlarmReceiver";
    Context context;

    public static final String COMMAND = "SENDER";
    public static final int SENDER_ACT_DOCUMENT = 0;
    public static final int SENDER_SRV_POSITIONING = 1;
    public static final int MIN_TIME_REQUEST = 5 * 1000;
    public static final String ACTION_REFRESH_SCHEDULE_ALARM =
            "org.mabna.order.ACTION_REFRESH_SCHEDULE_ALARM";
    private static Location currentLocation;
    private static Location prevLocation;
    private static Context _context;
    private String provider = LocationManager.GPS_PROVIDER;
    private static Intent _intent;
    private static LocationManager locationManager;

    // listener ----------------------------------------------------
    static ArrayList<OnNewLocationListener> arrOnNewLocationListener =
            new ArrayList<OnNewLocationListener>();

    // Allows the user to set a OnNewLocationListener outside of this class
    // and react to the event.
    // A sample is provided in ActDocument.java in method: startStopTryGetPoint


    @Override
    public void onReceive(Context context, Intent intent) {
context=context;
        Log.d(TAG, "onReceive: masukkkkk");
        if (intent.getAction() != null && context != null) {
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
                // Set the alarm here.
                Log.d(TAG, "onReceive: BOOT_COMPLETED");
//                LocalData localData = new LocalData(context);
//                Util.setReminder(context, AlarmReceiver.class,
//                        localData.get_hour(), localData.get_min());
                return;
            }
        }

    long id= intent.getIntExtra(Constant.RECORD_ID,0);
    String jam= intent.getStringExtra("jam");
    String iduser= intent.getStringExtra("iduser");
    String nama= intent.getStringExtra("nama");
    String lokasinama= intent.getStringExtra("lokasinama");
    String lokasilatlong= intent.getStringExtra("lokasilatlong");
    String lokasilatlongnow= Util.getSharedPreferenceString(context,PREFS_IS_latlong,"");
        Log.d(TAG, "onReceive: AlarmReceiver iduse"+iduser);
        //Trigger the notification
        Util.showNotification(context, MainActivity.class,
                "notifikasi Sholat", "Sholat "+nama);




//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("Test dialog");
//        builder.setIcon(R.drawable.icon_masjid);
//        builder.setMessage("Content");
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                //Do something
//                dialog.dismiss();
//            }});
//            builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int whichButton) {
//                    dialog.dismiss();
//                }
//            });
//            AlertDialog alert = builder.create();
//              alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//            alert.show();

//        Intent alarmIntent = new Intent("android.intent.action.MAIN");
//        alarmIntent.setClass(context, AlertDialogClass.class);
//        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Start the popup activity

        ;

        String latong[]=lokasilatlong.split(";");
        String latlongnow[]=lokasilatlongnow.split(";");
        double lat1= Util.parseDouble(latlongnow[0]);
        double long1= Util.parseDouble(latlongnow[1]);
        double lat2= Util.parseDouble(latong[0]);
        double long2= Util.parseDouble(latong[1]);

        LatLng Distancedlatlng_now = new LatLng(lat1, long1);
        LatLng Distancedlatlng_tujuan = new LatLng(lat2, long2);

        int meterInDec=   CalculationByDistance(Distancedlatlng_now,Distancedlatlng_tujuan);

        Log.d(TAG, "onReceive:meterInDec "+meterInDec);

        boolean isClosed=false;
        if (meterInDec<=10){
            isClosed=true;
        }

        //add detect 10 meter on locate auto save on ()record. . .
        Intent alarmIntent = new Intent(context,  AlertDialogClass.class)
                .putExtra(Constant.RECORD_ID, String.valueOf(id))
                .putExtra("jam",jam)
                .putExtra("nama",nama)
                .putExtra("iduser",iduser)
                .putExtra("lokasinama",lokasinama)
                .putExtra("lokasilatlong",lokasilatlong)
                .putExtra("isClosed",isClosed);


// Old activities shouldn't be in the history stack
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                0,
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);


        context.startActivity(alarmIntent);

    }

    public void setupAlarm(AlarmService alarmService) {
        Util.setReminder(alarmService, AlarmReceiver.class,14,02);
    }





    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * @returns Distance in Meters
     */
    public static int CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
//        Log.d("Radius Value", "" + valueResult + "   KM  " + kmInDec
//                + " Meter   " + meterInDec);

        return meterInDec;
//        return Radius * c;
    }


    private static boolean isLocationNew() {
        if (currentLocation == null) {
            return false;
        } else if (prevLocation == null) {
            return true;
        } else if (currentLocation.getTime() == prevLocation.getTime()) {
            return false;
        } else {
            return true;
        }
    }


}