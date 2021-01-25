package com.qulbs.prayertimesazan.util;

/**
 * Created by Feriyal on 11/22/2016.
 */

public interface Constant {
    String default_longitude = "112.619020";
    String default_latitude = "-7.938375";
    String default_simpledateFormat = "dd-M-yyyy HH:mm:ss";
    String PREFS_IS_LOGIN="PREFS_IS_LOGIN";
    String PREFS_IS_USER="PREFS_IS_USER";
    String PREFS_IS_latlong="PREFS_IS_latlong";

    String name_sholat_subuh ="fajr";
    String name_sholat_dhuhur="dhuhr";
    String name_sholat_ashar="asr";
    String name_sholat_maghrib ="maghrib";
    String name_sholat_isha ="isha";

    String name_time_sunrise ="sunrise";
    String name_time_sunset="sunset";
    String name_time_sepertigamalam="sepertigamalam";
    String name_time_tengahmalam="tengahmalam";
    String name_time_duapertigamalam="duapertigamalam";


    String RECORD_ID = "ID_TABLE";
    int NO_REPEAT = 1;
    int DAILY =2 ;
    int WEEKLY = 3;
    int MONTHLY = 4;
    int MONTHLY_2 = 5;
    int YEARLY = 6;

    long LOCATION_INTERVAL =1000;
    long FASTEST_LOCATION_INTERVAL =500;
}
