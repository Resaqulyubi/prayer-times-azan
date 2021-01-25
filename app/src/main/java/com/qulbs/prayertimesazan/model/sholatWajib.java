package com.qulbs.prayertimesazan.model;

public class sholatWajib {
    public String getStatus() {
        return status;
    }

    public sholatWajib setStatus(String status) {
        this.status = status;
        return this;
    }

    private String status;
    private Data data;
    private Time time;

    public Location getLocation() {
        return location;
    }

    public sholatWajib setLocation(Location location) {
        this.location = location;
        return this;
    }

    private Location location;



    public class Data {
        private String Fajr="";
        private String Sunrise="";
        private String Dhuhr="";
        private String Asr="";
        private String Sunset="";
        private String Maghrib="";
        private String Isha="";
        private String Imsak="";
        private String SepertigaMalam="";
        private String TengahMalam="";
        private String DuapertigaMalam="";

        public String getFajr() {
            return Fajr;
        }

        public Data setFajr(String fajr) {
            Fajr = fajr;
            return this;
        }

        public String getSunrise() {
            return Sunrise;
        }

        public Data setSunrise(String sunrise) {
            Sunrise = sunrise;
            return this;
        }

        public String getDhuhr() {
            return Dhuhr;
        }

        public Data setDhuhr(String dhuhr) {
            Dhuhr = dhuhr;
            return this;
        }

        public String getAsr() {
            return Asr;
        }

        public Data setAsr(String asr) {
            Asr = asr;
            return this;
        }

        public String getSunset() {
            return Sunset;
        }

        public Data setSunset(String sunset) {
            Sunset = sunset;
            return this;
        }

        public String getMaghrib() {
            return Maghrib;
        }

        public Data setMaghrib(String maghrib) {
            Maghrib = maghrib;
            return this;
        }

        public String getIsha() {
            return Isha;
        }

        public Data setIsha(String isha) {
            Isha = isha;
            return this;
        }

        public String getImsak() {
            return Imsak;
        }

        public Data setImsak(String imsak) {
            Imsak = imsak;
            return this;
        }

        public String getSepertigaMalam() {
            return SepertigaMalam;
        }

        public Data setSepertigaMalam(String sepertigaMalam) {
            SepertigaMalam = sepertigaMalam;
            return this;
        }

        public String getTengahMalam() {
            return TengahMalam;
        }

        public Data setTengahMalam(String tengahMalam) {
            TengahMalam = tengahMalam;
            return this;
        }

        public String getDuapertigaMalam() {
            return DuapertigaMalam;
        }

        public Data setDuapertigaMalam(String duapertigaMalam) {
            DuapertigaMalam = duapertigaMalam;
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

    public sholatWajib() {
    }

    public sholatWajib(Boolean status, Data data, String msg) {
        this.data = data;
        this.msg = msg;
    }


    public Data getData() {
        return data;
    }

    public sholatWajib setData(Data data) {
        this.data = data;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public sholatWajib setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
