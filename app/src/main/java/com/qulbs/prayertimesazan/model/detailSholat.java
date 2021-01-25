package com.qulbs.prayertimesazan.model;

import java.util.List;

public class detailSholat {
    private String status;
    private List<Data> data;





    public List<Data> getData() {
        return data;
    }

    public detailSholat setData(List<Data> data) {
        this.data = data;
        return this;
    }




    public class Data {
        private String id="";
        private String iduser="";
        private String idkategori="";
        private String latlong="";
        private String jam="";

        public String getCreatedate() {
            return createdate;
        }

        public Data setCreatedate(String createdate) {
            this.createdate = createdate;
            return this;
        }

        private String createdate="";

        public String getNama_lokasi() {
            return nama_lokasi;
        }

        public Data setNama_lokasi(String nama_lokasi) {
            this.nama_lokasi = nama_lokasi;
            return this;
        }

        private String nama_lokasi="";


        public String getId() {
            return id;
        }

        public Data setId(String id) {
            this.id = id;
            return this;
        }

        public String getIduser() {
            return iduser;
        }

        public Data setIduser(String iduser) {
            this.iduser = iduser;
            return this;
        }

        public String getIdkategori() {
            return idkategori;
        }

        public Data setIdkategori(String idkategori) {
            this.idkategori = idkategori;
            return this;
        }

        public String getLatlong() {
            return latlong;
        }

        public Data setLatlong(String latlong) {
            this.latlong = latlong;
            return this;
        }

        public String getJam() {
            return jam;
        }

        public Data setJam(String jam) {
            this.jam = jam;
            return this;
        }







        public Data() {
        }




    }



    public String getStatus() {
        return status;
    }

    public detailSholat setStatus(String status) {
        this.status = status;
        return this;
    }






    public detailSholat() {
    }

}
