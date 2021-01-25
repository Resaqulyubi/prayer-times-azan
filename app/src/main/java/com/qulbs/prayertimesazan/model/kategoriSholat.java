package com.qulbs.prayertimesazan.model;

import java.util.List;

public class kategoriSholat {
    private String status;
    private List<Data> data;





    public List<Data> getData() {
        return data;
    }

    public kategoriSholat setData(List<Data> data) {
        this.data = data;
        return this;
    }




    public class Data {
        private String id="";
        private String nama="";
        private String is_wajib="";
        private String jam_start="";
        private String jam_end="";

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

        public String getIs_wajib() {
            return is_wajib;
        }

        public Data setIs_wajib(String is_wajib) {
            this.is_wajib = is_wajib;
            return this;
        }

        public String getJam_start() {
            return jam_start;
        }

        public Data setJam_start(String jam_start) {
            this.jam_start = jam_start;
            return this;
        }

        public String getJam_end() {
            return jam_end;
        }

        public Data setJam_end(String jam_end) {
            this.jam_end = jam_end;
            return this;
        }











        public Data() {
        }




    }



    public String getStatus() {
        return status;
    }

    public kategoriSholat setStatus(String status) {
        this.status = status;
        return this;
    }






    public kategoriSholat() {
    }

}
