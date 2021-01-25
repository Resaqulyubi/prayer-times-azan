package com.qulbs.prayertimesazan.model.db;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import com.qulbs.prayertimesazan.model.sholatWajib;

/**
 * Created by WINDOWS 10 on 13/02/2017.
 */

public class SinkronisasiSholatWajibDB extends SugarRecord {
    @Unique
    private String fajr="";
    private String Sunrise="";
    private String Dhuhr="";

    public String getFajr() {
        return fajr;
    }

    public SinkronisasiSholatWajibDB setFajr(String fajr) {
        this.fajr = fajr;
        return this;
    }

    public String getSunrise() {
        return Sunrise;
    }

    public SinkronisasiSholatWajibDB setSunrise(String sunrise) {
        Sunrise = sunrise;
        return this;
    }

    public String getDhuhr() {
        return Dhuhr;
    }

    public SinkronisasiSholatWajibDB setDhuhr(String dhuhr) {
        Dhuhr = dhuhr;
        return this;
    }

    public String getAsr() {
        return Asr;
    }

    public SinkronisasiSholatWajibDB setAsr(String asr) {
        Asr = asr;
        return this;
    }

    public String getSunset() {
        return Sunset;
    }

    public SinkronisasiSholatWajibDB setSunset(String sunset) {
        Sunset = sunset;
        return this;
    }

    public String getMaghrib() {
        return Maghrib;
    }

    public SinkronisasiSholatWajibDB setMaghrib(String maghrib) {
        Maghrib = maghrib;
        return this;
    }

    public String getIsha() {
        return Isha;
    }

    public SinkronisasiSholatWajibDB setIsha(String isha) {
        Isha = isha;
        return this;
    }

    public String getImsak() {
        return Imsak;
    }

    public SinkronisasiSholatWajibDB setImsak(String imsak) {
        Imsak = imsak;
        return this;
    }

    public String getSepertigaMalam() {
        return SepertigaMalam;
    }

    public SinkronisasiSholatWajibDB setSepertigaMalam(String sepertigaMalam) {
        SepertigaMalam = sepertigaMalam;
        return this;
    }

    public String getTengahMalam() {
        return TengahMalam;
    }

    public SinkronisasiSholatWajibDB setTengahMalam(String tengahMalam) {
        TengahMalam = tengahMalam;
        return this;
    }

    public String getDuapertigaMalam() {
        return DuapertigaMalam;
    }

    public SinkronisasiSholatWajibDB setDuapertigaMalam(String duapertigaMalam) {
        DuapertigaMalam = duapertigaMalam;
        return this;
    }

    public String getLatitude() {
        return latitude;
    }

    public SinkronisasiSholatWajibDB setLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public String getLongitude() {
        return longitude;
    }

    public SinkronisasiSholatWajibDB setLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public String getTanggal() {
        return tanggal;
    }

    public SinkronisasiSholatWajibDB setTanggal(String tanggal) {
        this.tanggal = tanggal;
        return this;
    }

    private String Asr="";
    private String Sunset="";
    private String Maghrib="";
    private String Isha="";
    private String Imsak="";
    private String SepertigaMalam="";
    private String TengahMalam="";
    private String DuapertigaMalam="";
    private String latitude="";
    private String longitude="";
    private String tanggal="";

    public SinkronisasiSholatWajibDB() {
    }

    public SinkronisasiSholatWajibDB(sholatWajib data) {
        this.fajr = data.getData().getFajr();
        this.Sunrise = data.getData().getSunrise();
        this.Dhuhr = data.getData().getDhuhr();
        this.Asr = data.getData().getAsr();
        this.Sunset = data.getData().getSunset();
        this.Maghrib = data.getData().getMaghrib();
        this.Isha = data.getData().getIsha();
        this.Imsak = data.getData().getImsak();
        this.SepertigaMalam = data.getData().getSepertigaMalam();
        this.TengahMalam = data.getData().getTengahMalam();
        this.DuapertigaMalam = data.getData().getDuapertigaMalam();
        this.latitude = data.getLocation().getLatitude();
        this.longitude = data.getLocation().getLongitude();

    }



}
