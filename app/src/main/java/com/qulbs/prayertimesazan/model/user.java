package com.qulbs.prayertimesazan.model;

import java.util.List;

public class user {


    public boolean isStatus() {
        return status;
    }

    public user setStatus(boolean status) {
        this.status = status;
        return this;
    }

    private boolean status;

    public List<Data> getData() {
        return data;
    }

    public user setData(List<Data> data) {
        this.data = data;
        return this;
    }

    private List<Data> data;



    public class Data {
        private String id="";
        private String nama="";
        private String password="";
        private String email="";
        private String username="";


        public String getId() {
            return id;
        }

        public Data setId(String id) {
            this.id = id;
            return this;
        }

        public String getNama() {
            return nama;
        }

        public Data setNama(String nama) {
            this.nama = nama;
            return this;
        }

        public String getPassword() {
            return password;
        }

        public Data setPassword(String password) {
            this.password = password;
            return this;
        }

        public String getEmail() {
            return email;
        }

        public Data setEmail(String email) {
            this.email = email;
            return this;
        }

        public String getUsername() {
            return username;
        }

        public Data setUsername(String username) {
            this.username = username;
            return this;
        }







        public Data() {
        }




    }


    public class Time{
        private String date="";
        private String time="";

    }

    public class Location{
        private String latitude="";
        private String longitude="";

        public String getLatitude() {
            return latitude;
        }

        public Location setLatitude(String latitude) {
            this.latitude = latitude;
            return this;
        }

        public String getLongitude() {
            return longitude;
        }

        public Location setLongitude(String longitude) {
            this.longitude = longitude;
            return this;
        }



        public Location(){

        }

    }



    private String msg;

    public user() {
    }



    public String getMsg() {
        return msg;
    }

    public user setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
