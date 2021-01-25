package com.qulbs.prayertimesazan.model.db;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import static com.qulbs.prayertimesazan.util.Util.dateToMillis;
import static com.qulbs.prayertimesazan.util.Util.dateToMillis;

/**
 * Created by WINDOWS 10 on 13/02/2017.
 */

public class SholatWajibDB extends SugarRecord implements Comparable<SholatWajibDB> {
    @Unique
    private String nama="";
    private String waktu="";
    private String aktif="";
    private String iswajib="";

    public String getIdkategori() {
        return idkategori;
    }

    public SholatWajibDB setIdkategori(String idkategori) {
        this.idkategori = idkategori;
        return this;
    }

    private String idkategori="";

    @Override
    public int compareTo(SholatWajibDB o) {

        long compareFav = 0;

        if( dateToMillis(o.getWaktu(),"HH:mm") > dateToMillis(waktu,"HH:mm") ){
            return 1;
        }else {
            return -1;
        }
    }

    public String getIssunah() {
        return issunah;
    }

    public SholatWajibDB setIssunah(String issunah) {
        this.issunah = issunah;
        return this;
    }

    private String issunah="";
    private String iduser="";

    public String getIswajib() {
        return iswajib;
    }

    public SholatWajibDB setIswajib(String iswajib) {
        this.iswajib = iswajib;
        return this;
    }




    public SholatWajibDB setIduser(String iduser) {
        this.iduser = iduser;
        return this;
    }

    public String getIduser() {
        return iduser;
    }


    public String getNama() {
        return nama;
    }

    public SholatWajibDB setNama(String nama) {
        this.nama = nama;
        return this;
    }

    public String getWaktu() {
        return waktu;
    }

    public SholatWajibDB setWaktu(String waktu) {
        this.waktu = waktu;
        return this;
    }

    public String getAktif() {
        return aktif;
    }

    public SholatWajibDB setAktif(String aktif) {
        this.aktif = aktif;
        return this;
    }



}
