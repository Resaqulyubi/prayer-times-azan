package com.qulbs.prayertimesazan.model.db;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import com.qulbs.prayertimesazan.model.kategoriSholat;

/**
 * Created by WINDOWS 10 on 13/02/2017.
 */

public class kategoriSholatDB extends SugarRecord {
    @Unique
    private String idkategori="";
    private String nama="";
    private String iswajib="";
    private String jamstart="";
    private String jamend="";

    public String getIswajib() {
        return iswajib;
    }

    public kategoriSholatDB setIswajib(String iswajib) {
        this.iswajib = iswajib;
        return this;
    }

    public String getJamstart() {
        return jamstart;
    }

    public kategoriSholatDB setJamstart(String jamstart) {
        this.jamstart = jamstart;
        return this;
    }

    public String getJamend() {
        return jamend;
    }

    public kategoriSholatDB setJamend(String jamend) {
        this.jamend = jamend;
        return this;
    }



    public String getIdkategori() {
        return idkategori;
    }

    public kategoriSholatDB setIdkategori(String idkategori) {
        this.idkategori = idkategori;
        return this;
    }

    public String getNama() {
        return nama;
    }

    public kategoriSholatDB setNama(String nama) {
        this.nama = nama;
        return this;
    }





    public kategoriSholatDB(){

    }
    public kategoriSholatDB(kategoriSholat.Data data){
        idkategori= data.getId();
        nama= data.getNama();
        iswajib= data.getIs_wajib();
        jamstart= data.getJam_start();
        jamend= data.getJam_end();

    }







}
