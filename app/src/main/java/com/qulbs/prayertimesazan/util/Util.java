package com.qulbs.prayertimesazan.util;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.joda.time.MutableDateTime;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import com.qulbs.prayertimesazan.Alarm.AlarmReceiver;
import com.qulbs.prayertimesazan.R;
import com.qulbs.prayertimesazan.model.db.SholatWajibDB;
import com.qulbs.prayertimesazan.model.db.SinkronisasiSholatWajibDB;

import static android.content.Context.ALARM_SERVICE;

public class Util {

    public static final String PREFS_NAME = "_ReminderIbadahPref";
    private static final String TAG = "Util";
    private static final int DAILY_REMINDER_REQUEST_CODE = 4000;

    public static String getSharedPreferenceString(Context c, String preference, String defaultValue) {
        try {
            SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
            return settings.getString(preference, defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static final String getCurrentDate(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    public static int getDimenResources(Context context, int resource){
        return (int) (context.getResources().getDimension(resource) / context.getResources().getDisplayMetrics().density);
    }

    public static int getSharedPreferenceInteger(Context c, String preference, int defaultValue) {
        try {
            SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
            return settings.getInt(preference, defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }
    public static final long dateToMillis(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date).getTime();
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }

        return -1;
    }

    public static final Date dateToMillisDATE(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date);
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }

        return null;
    }


    public static List<String> getTimeSholatClose(SinkronisasiSholatWajibDB SinkronisasiSholatWajibDB){
        List<String> strings=new ArrayList<>();
        Log.d(TAG, "getTimeSholatClose: now is "+getCurrentDate("HH:mm"));
        long timeNow= dateToMillis(getCurrentDate("HH:mm"),"HH:mm");
        if ( timeNow> dateToMillis(SinkronisasiSholatWajibDB.getIsha(),"HH:mm")||
                ((timeNow> dateToMillis("00:00","HH:mm")&&(timeNow<=dateToMillis(SinkronisasiSholatWajibDB.getFajr(),"HH:mm")) ))){
            strings.add(SinkronisasiSholatWajibDB.getFajr());
            strings.add("Subuh (Fajr)");
        }else if(timeNow<= dateToMillis(SinkronisasiSholatWajibDB.getDhuhr(),"HH:mm")&&
                timeNow> dateToMillis(SinkronisasiSholatWajibDB.getFajr(),"HH:mm")){
            strings.add(SinkronisasiSholatWajibDB.getDhuhr());
            strings.add("Dzuhur");
        }else if(timeNow<= dateToMillis(SinkronisasiSholatWajibDB.getAsr(),"HH:mm")&&
                timeNow> dateToMillis(SinkronisasiSholatWajibDB.getDhuhr(),"HH:mm")) {
            strings.add(SinkronisasiSholatWajibDB.getAsr());
            strings.add("Azhar");

        }else if(timeNow<= dateToMillis(SinkronisasiSholatWajibDB.getMaghrib(),"HH:mm")&&
                timeNow> dateToMillis(SinkronisasiSholatWajibDB.getAsr(),"HH:mm")){
            strings.add(SinkronisasiSholatWajibDB.getMaghrib());
            strings.add("Maghrib");
        }
        else if(timeNow<= dateToMillis(SinkronisasiSholatWajibDB.getIsha(),"HH:mm")&&
                timeNow> dateToMillis(SinkronisasiSholatWajibDB.getMaghrib(),"HH:mm")){
            strings.add(SinkronisasiSholatWajibDB.getIsha());
            strings.add("Isya");
        }else {
            strings.add("00:00");
            strings.add("");
        }
        return  strings;
    }


    public static SholatWajibDB getClosedOne(){
//        List<SholatWajibDB> wajibDBSdb=new ArrayList<>();
//        wajibDBSdb.add(new SholatWajibDB().setWaktu("07:50"));
//        wajibDBSdb.add(new SholatWajibDB().setWaktu("12:20"));
//        wajibDBSdb.add(new SholatWajibDB().setWaktu("05:50"));
//        wajibDBSdb.add(new SholatWajibDB().setWaktu("10:01"));
//        wajibDBSdb.add(new SholatWajibDB().setWaktu("19:04"));
////        wajibDBSdb.add(new SholatWajibDB().setWaktu("23:01"));
//        wajibDBSdb.add(new SholatWajibDB().setWaktu("02:01"));
//

        List<SholatWajibDB> wajibDBSdb=SholatWajibDB.find(SholatWajibDB.class,"aktif='1' AND waktu!='' ");
        SholatWajibDB db=new SholatWajibDB() ;
        final long now = System.currentTimeMillis();
        Log.d(TAG, "getClosedOne: now is "+getCurrentDate("HH:mm"));



// Create a sample list of dates
//        List<Date> dates = new ArrayList<Date>();
//        Random r = new Random();
//        for (int i = 0; i < 10; i++) dates.add(new Date(now + r.nextInt(10000)-5000));

// Get date closest to "now"
//        Date closest = Collections.min(dates, new Comparator<Date>() {
//            public int compare(Date d1, Date d2) {
//                long diff1 = Math.abs(d1.getTime() - now);
//                long diff2 = Math.abs(d2.getTime() - now);
//                return Long.compare(diff1, diff2);
//            }
//        });
        Date timeNow= (new Date());
//        if (timeNow> dateToMillis(SinkronisasiSholatWajibDB.getIsha(),"HH:mm")){
//        SholatWajibDB closest = Collections.min(wajibDBSdb, new Comparator<SholatWajibDB>() {
//            public int compare(SholatWajibDB d1, SholatWajibDB d2) {
//                long diff1 = Math.abs(dateToMillis(d1.getWaktu(),"HH:mm")  - now);
//                long diff2 = Math.abs( dateToMillis(d2.getWaktu(),"HH:mm") - now);
//                return Long.compare(diff1, diff2);
//            }
//        });

//        Log.d(TAG, "getClosedOne: "+closest.getWaktu());

        Collections.sort(wajibDBSdb,
                (o1, o2) -> o1.getWaktu().compareTo(o2.getWaktu()));
//
        for (int i = 0; i <wajibDBSdb.size() ; i++) {
            Log.d(TAG, "getClosedOne: "+wajibDBSdb.get(i).getWaktu());
        }

        return db;
    }


    public static final int toDIP(Context pContext, float p_value) {
        return (int) ((p_value * pContext.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static SholatWajibDB closerDate2() {
        Date originalDate = new Date();
        List<SholatWajibDB> wajibDBSdb=SholatWajibDB.find(SholatWajibDB.class,"aktif='1' AND waktu!='' ");
        Date previousDate = null;
        SholatWajibDB db = new SholatWajibDB();
        for (int i = 0; i < wajibDBSdb.size(); i++) {
            Date nextDate = dateToMillisDATE(wajibDBSdb.get(i).getWaktu(),"HH:mm");
            if (nextDate.before(originalDate)) {
                previousDate = nextDate;
                continue;
            } else if (nextDate.after(originalDate)) {
                if (previousDate == null || isCloserToNextDate(originalDate, previousDate, nextDate)) {
                  db = wajibDBSdb.get(i);
                 break;
                }
            } else {
            db= wajibDBSdb.get(i);
            break;
            } }

        return db;
    }


    public static SholatWajibDB closerDate() {
        Date originalDate = new Date();
        List<SholatWajibDB> wajibDBSdb=SholatWajibDB.find(SholatWajibDB.class,"aktif='1' AND waktu!='' ");
        Date previousDate = null;
        TreeSet<LocalTime> times = new TreeSet<>();

//        for (int i = 0; i < wajibDBSdb.size(); i++) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                times.add(LocalTime.parse(wajibDBSdb.get(i).getWaktu()));
//            }else {
//                DateTimeFormatter f2=DateTimeFormatter.ofPattern("hh mm a");
//                LocalTime t=LocalTime.parse("11 08 AM",f2); //exception here
//                times
//            }
//        }

//        times.add(LocalTime.parse("06:40"));
//        times.add(LocalTime.parse("08:30"));
//...

//        LocalTime ceiling = times.ceiling(LocalTime.now());
//        if (ceiling != null) //do something with it








        return null;
    }

    public static Date getNearestDate(List<Date> dates, Date currentDate) {
        long minDiff = -1, currentTime = currentDate.getTime();
        Date minDate = null;
        for (Date date : dates) {
            long diff = Math.abs(currentTime - date.getTime());
            if ((minDiff == -1) || (diff < minDiff)) {
                minDiff = diff;
                minDate = date;
            }
        }
        return minDate;
    }


    private static boolean isCloserToNextDate(Date originalDate, Date previousDate, Date nextDate) {
        if(previousDate.after(nextDate))
            throw new IllegalArgumentException("previousDate > nextDate");
        return ((nextDate.getTime() - previousDate.getTime()) / 2 + previousDate.getTime() <= originalDate.getTime());
    }


    public static boolean getSharedPreferenceBoolean(Context c, String preference, boolean defaultValue) {
        try {
            SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
            return settings.getBoolean(preference, defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static void setSharedPreference(Context c, String preference, String prefValue) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(preference, prefValue);
        editor.commit();
    }

    public static void setSharedPreference(Context c, String preference, boolean prefValue) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(preference, prefValue);
        editor.commit();
    }

    public static void removeSharedPreference(Context c, String preference) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(preference);
        editor.commit();
    }

    public static void clearSharedPreference(Context c) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
    }

    public static void setReminder(Context context, Class<?> cls, int hour, int min)
    {
        Calendar calendar = Calendar.getInstance();
        Calendar setcalendar = Calendar.getInstance();
        setcalendar.set(Calendar.HOUR_OF_DAY, hour);
        setcalendar.set(Calendar.MINUTE, min);
        setcalendar.set(Calendar.SECOND, 0);
        // cancel already scheduled reminders
        cancelReminder(context,cls);

        if(setcalendar.before(calendar)){
            setcalendar.add(Calendar.DATE,1);
        }

        // Enable a receiver
        ComponentName receiver = new ComponentName(context, cls);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        Intent intent1 = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                DAILY_REMINDER_REQUEST_CODE, intent1,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, setcalendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);

        Log.d(TAG, "setReminder: "+setcalendar);
    }


    public static void setAlarmcek(Context context){
        Log.d("Carbon","Alrm SET !!");

        // get a Calendar object with current time
        Calendar cal = Calendar.getInstance();
        // add 30 seconds to the calendar object
        cal.add(Calendar.SECOND, 30);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 192837, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get the AlarmManager service
        AlarmManager am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);
    }

    public static void AddAlarm(Context context, int requestCode, MutableDateTime dueDate, int repeat, String iduser, String jam, String nama,
                                String lokasinama, String lokasilatlong, boolean iscancel) {

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(Constant.RECORD_ID, requestCode);
        intent.putExtra("REPEAT", repeat);
        intent.putExtra("jam", jam);
        intent.putExtra("iduser", iduser);
        intent.putExtra("nama", nama);
        intent.putExtra("lokasinama", lokasinama);
        intent.putExtra("lokasilatlong", lokasilatlong);



        // https://stackoverflow.com/questions/7709030/get-gps-location-in-a-broadcast-receiver-or-service-to-broadcast-receiver-data-t?answertab=votes#tab-top


//        PendingIntent operation = PendingIntent.getBroadcast(context, requestCode, intent,  PendingIntent.FLAG_UPDATE_CURRENT );
        PendingIntent operation = PendingIntent.getBroadcast(context, requestCode, intent,  PendingIntent.FLAG_ONE_SHOT );

        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        if (iscancel){
            am.cancel(operation);
            Log.d(TAG, "setReminder cancel: "+dueDate.toString());
        }else {
         am.cancel(operation);
        MutableDateTime due = dueDate.toMutableDateTime();
        switch(repeat){
            case Constant.NO_REPEAT:
                due.addMinutes(0);
                break;
            case Constant.DAILY:
                due.addDays(1);
                break;
            case Constant.WEEKLY:
                due.addWeeks(1);
                break;
            case Constant.MONTHLY:
                due.addMonths(1);
                break;
            case Constant.MONTHLY_2:
                due.addWeeks(5);
                break;
            case Constant.YEARLY:
                due.addYears(1);
                break;
        }
        due.add(-(dueDate.getMillis()));
        due.setSecondOfMinute(0);
        dueDate.setSecondOfMinute(0);

        am.set(AlarmManager.RTC_WAKEUP, dueDate.getMillis(), operation);
        am.setRepeating(AlarmManager.RTC_WAKEUP, dueDate.getMillis(), due.getMillis(), operation);
        Log.d(TAG, "setReminder: "+dueDate.toString());
        }
    }

       public static Double parseDouble(String value) {

        if (value == null) {
            value = "0";
        } else if (value.isEmpty()) {
            value = "0";
        } else if (value.contains(",")){
            value = value.replace(",", ".");
        } else if (Double.parseDouble(value) < 0) {
            value = "0";
        } else if (value.equalsIgnoreCase("nan")) {
            value = "0";
        }

        return Double.parseDouble(value);
    }


    public static Integer parseInteger(String value) {
        try {
            if (value == null) {
                value = "0";
            } else if (value.isEmpty()) {
                value = "0";
            } else if (Double.parseDouble(value) < 0) {
                value = "0";
            } else if (value.equalsIgnoreCase("nan")) {
                value = "0";
            }
            float tmp = Float.parseFloat(value);
            return Math.round(tmp);
        } catch (NumberFormatException e){
            return 0;
        }
    }

    public static Long parseLong(String value) {
        if (value == null) {
            value = "0";
        } else if (value.isEmpty()) {
            value = "0";
        } else if (Double.parseDouble(value) < 0) {
            value = "0";
        } else if (value.equalsIgnoreCase("nan")) {
            value = "0";
        }

        return Long.parseLong(value);
    }


    public static String getTimeAgo2(Date past){

        try
        {
//            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss");
//            Date past = format.parse("2016.02.05 AD at 23:59:30");
            Date now = new Date();
            long seconds= TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime());
            long minutes= TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
            long hours= TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
            long days= TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());
//

            Log.d(TAG, "getTimeAgo2: ");
          System.out.println(TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime()) + " milliseconds ago");
          System.out.println(TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime()) + " minutes ago");
          System.out.println(TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime()) + " hours ago");
          System.out.println(TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime()) + " days ago");

            if(seconds<60)
            {
                System.out.println(seconds+" seconds ago");
                return seconds+" seconds ago";
            }
            else if(minutes<60)
            {
                System.out.println(minutes+" minutes ago");
                return minutes+" minutes ago";
            }
            else if(hours<24)
            {
                System.out.println(hours+" hours ago");

                return hours+" hours ago";
            }
            else
            {
                System.out.println(days+" days ago");

                return days+" days ago";
            }
        }
        catch (Exception j){
            j.printStackTrace();
        }


        return "";
    }


    public static String getTimeAgo(long pastMillis, long currentMillis) {
        long duration = currentMillis - pastMillis;
        long ONE_SECOND = 1000;
        long ONE_MINUTE = ONE_SECOND * 60;
        long ONE_HOUR = ONE_MINUTE * 60;
        long ONE_DAY = ONE_HOUR * 24;

        long temp = 0;
        if (duration >= ONE_SECOND) {
            temp = duration / ONE_DAY;
            if (temp > 0) {
                return temp + " hari yang lalu";
            }

            temp = duration / ONE_HOUR;
            if (temp > 0) {
                return temp + " jam yang lalu";
            }

            temp = duration / ONE_MINUTE;
            if (temp > 0) {
                return temp + " menit yang lalu";
            } else {
                return "beberapa detik yang lalu";
            }
        }

        return "beberapa detik yang lalu";
    }


    public static void cancelReminder(Context context, Class<?> cls)
    {
        // Disable a receiver
        ComponentName receiver = new ComponentName(context, cls);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);

        Intent intent1 = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                DAILY_REMINDER_REQUEST_CODE, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.cancel(pendingIntent);
        pendingIntent.cancel();
    }

    public static void showNotification(Context context, Class<?> cls, String title, String content)
    {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent notificationIntent = new Intent(context, cls);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(cls);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(
                DAILY_REMINDER_REQUEST_CODE, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Notification notification = builder.setContentTitle(title)
                .setContentText(content).setAutoCancel(true)
                .setSound(alarmSound).setSmallIcon(R.drawable.icon_masjid)
                .setContentIntent(pendingIntent).build();

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(DAILY_REMINDER_REQUEST_CODE, notification);
    }
}
