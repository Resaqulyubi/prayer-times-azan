package com.qulbs.prayertimesazan.model.db;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import com.qulbs.prayertimesazan.model.detailSholat;

/**
 * Created by WINDOWS 10 on 13/02/2017.
 */

public class detailSholatDB extends SugarRecord {
    @Unique
    private String iddetail="";

    public String getIddetail() {
        return iddetail;
    }

    public detailSholatDB setIddetail(String iddetail) {
        this.iddetail = iddetail;
        return this;
    }

    public String getIduser() {
        return iduser;
    }

    public detailSholatDB setIduser(String iduser) {
        this.iduser = iduser;
        return this;
    }

    public String getIdkategori() {
        return idkategori;
    }

    public detailSholatDB setIdkategori(String idkategori) {
        this.idkategori = idkategori;
        return this;
    }

    public String getLatlong() {
        return latlong;
    }

    public detailSholatDB setLatlong(String latlong) {
        this.latlong = latlong;
        return this;
    }

    public String getJam() {
        return jam;
    }

    public detailSholatDB setJam(String jam) {
        this.jam = jam;
        return this;
    }

    private String iduser="";
    private String idkategori="";
    private String latlong="";
    private String jam="";

    public String getCreatedate() {
        return createdate;
    }

    public detailSholatDB setCreatedate(String createdate) {
        this.createdate = createdate;
        return this;
    }

    private String createdate="";

    public String getNamalokasi() {
        return namalokasi;
    }

    public detailSholatDB setNamalokasi(String namalokasi) {
        this.namalokasi = namalokasi;
        return this;
    }

    private String namalokasi="";







    public detailSholatDB(){

    }
    public detailSholatDB(detailSholat.Data data){
        iddetail= data.getId();
        idkategori= data.getIdkategori();
        iduser= data.getIduser();
        latlong= data.getLatlong();
        jam= data.getJam();
        namalokasi= data.getNama_lokasi();
        createdate= data.getCreatedate();

    }







}
