package com.qulbs.prayertimesazan.model.db;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import com.qulbs.prayertimesazan.model.user;

/**
 * Created by WINDOWS 10 on 13/02/2017.
 */

public class userDB extends SugarRecord {
    @Unique
    private String iduser="";
    private String nama="";
    private String password="";
    private String email="";
    private String username="";


    public String getIduser() {
        return iduser;
    }

    public userDB setIduser(String iduser) {
        this.iduser = iduser;
        return this;
    }

    public userDB(){

    }
    public userDB(user.Data data){
        iduser= data.getId();
        nama= data.getNama();
        password= data.getPassword();
        email= data.getEmail();
        username= data.getUsername();

    }



    public String getNama() {
        return nama;
    }

    public userDB setNama(String nama) {
        this.nama = nama;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public userDB setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public userDB setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public userDB setUsername(String username) {
        this.username = username;
        return this;
    }



}
