package com.qulbs.prayertimesazan.model.db;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

/**
 * Created by WINDOWS 10 on 13/02/2017.
 */

public class lokasiSholatDB extends SugarRecord {
    @Unique
    private String nama="";

    public String getLatlong() {
        return latlong;
    }

    public lokasiSholatDB setLatlong(String latlong) {
        this.latlong = latlong;
        return this;
    }

    public String getIduser() {
        return iduser;
    }

    public lokasiSholatDB setIduser(String iduser) {
        this.iduser = iduser;
        return this;
    }

    public String getLocationname() {
        return locationname;
    }

    public lokasiSholatDB setLocationname(String locationname) {
        this.locationname = locationname;
        return this;
    }

    private String latlong ="";
    private String iduser="";
    private String locationname="";

    public String getAktif() {
        return aktif;
    }

    public lokasiSholatDB setAktif(String aktif) {
        this.aktif = aktif;
        return this;
    }

    private String aktif="";



    public String getNama() {
        return nama;
    }

    public lokasiSholatDB setNama(String nama) {
        this.nama = nama;
        return this;
    }





    public lokasiSholatDB(){

    }








}
