package com.need.unknown.component.model;

public class DataUser {

    /**
     * error : false
     * message :
     * data : {"user_id":31114,"name":"Gillan","email":"cross.envy63@gmail.com","phone_number":"087809651550","role":null,"is_agent":1,"is_upload":1,"total_affiliasi":0,"total_pendapatan":0,"force_logout":"0","kode_affiliasi":"BSELWNDV","nama_agen":null,"need_verify":0,"pin_enable":"1","jenis_kelamin":null,"tgl_lahir":null,"profile_url":null,"wallet":"Rp 2.540.166","wallet_amount":2540166,"poin":0,"is_need_pin":true,"mobile_info":[{"id":23,"image_url":"https://bisatopup.sgp1.digitaloceanspaces.com/banner/5d357175c532e.png","company_id":1,"url":"www.google.com","title":"Highcharts Demo 12","body":"<p>testing<\/p>","decription":null,"order_by":1,"version":1,"is_active":1,"menu":1}]}
     */

    private boolean error;
    private String message;
    private DataBean data;
    /**
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjMxMTE0LCJpc3MiOiJodHRwOi8vZGV2LmFtYW5haGNvcnAuY28uaWQvYXBpX2Jpc2F0b3B1cC9hdXRoL2xvZ2luIiwiaWF0IjoxNTcyNzc1MjA1LCJleHAiOjE1NzI3Nzg4MDUsIm5iZiI6MTU3Mjc3NTIwNSwianRpIjoiVEpXMzlGeUUxd053UGFoZiJ9.yQpS0PXRIZxQ26QQZ-qn1NxVZ-TXB5n05ict2-4H0Dc
     */

    private String token;
    /**
     * is_registered : true
     */

    private boolean is_registered;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isIs_registered() {
        return is_registered;
    }

    public void setIs_registered(boolean is_registered) {
        this.is_registered = is_registered;
    }

    public static class DataBean {
        /**
         * user_id : 31114
         * name : Gillan
         * email : cross.envy63@gmail.com
         * phone_number : 087809651550
         * role : null
         * is_agent : 1
         * is_upload : 1
         * total_affiliasi : 0
         * total_pendapatan : 0
         * force_logout : 0
         * kode_affiliasi : BSELWNDV
         * nama_agen : null
         * need_verify : 0
         * pin_enable : 1
         * jenis_kelamin : null
         * tgl_lahir : null
         * profile_url : null
         * wallet : Rp 2.540.166
         * wallet_amount : 2540166
         * poin : 0
         * is_need_pin : true
         * mobile_info : [{"id":23,"image_url":"https://bisatopup.sgp1.digitaloceanspaces.com/banner/5d357175c532e.png","company_id":1,"url":"www.google.com","title":"Highcharts Demo 12","body":"<p>testing<\/p>","decription":null,"order_by":1,"version":1,"is_active":1,"menu":1}]
         */

        private int user_id;
        private String name;
        private String email;
        private String phone_number;
        private String role;
        private int is_agent;
        private int is_upload;
        private int total_affiliasi;
        private int total_pendapatan;
        private String force_logout;
        private String kode_affiliasi;
        private String nama_agen;
        private int need_verify;
        private String pin_enable;
        private String jenis_kelamin;
        private String tgl_lahir;
        private String profile_url;
        private String wallet;
        private int wallet_amount;
        private int poin;
        private boolean is_need_pin;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public int getIs_agent() {
            return is_agent;
        }

        public void setIs_agent(int is_agent) {
            this.is_agent = is_agent;
        }

        public int getIs_upload() {
            return is_upload;
        }

        public void setIs_upload(int is_upload) {
            this.is_upload = is_upload;
        }

        public int getTotal_affiliasi() {
            return total_affiliasi;
        }

        public void setTotal_affiliasi(int total_affiliasi) {
            this.total_affiliasi = total_affiliasi;
        }

        public int getTotal_pendapatan() {
            return total_pendapatan;
        }

        public void setTotal_pendapatan(int total_pendapatan) {
            this.total_pendapatan = total_pendapatan;
        }

        public String getForce_logout() {
            return force_logout;
        }

        public void setForce_logout(String force_logout) {
            this.force_logout = force_logout;
        }

        public String getKode_affiliasi() {
            return kode_affiliasi;
        }

        public void setKode_affiliasi(String kode_affiliasi) {
            this.kode_affiliasi = kode_affiliasi;
        }

        public String getNama_agen() {
            return nama_agen;
        }

        public void setNama_agen(String nama_agen) {
            this.nama_agen = nama_agen;
        }

        public int getNeed_verify() {
            return need_verify;
        }

        public void setNeed_verify(int need_verify) {
            this.need_verify = need_verify;
        }

        public String getPin_enable() {
            return pin_enable;
        }

        public void setPin_enable(String pin_enable) {
            this.pin_enable = pin_enable;
        }

        public String getJenis_kelamin() {
            return jenis_kelamin;
        }

        public void setJenis_kelamin(String jenis_kelamin) {
            this.jenis_kelamin = jenis_kelamin;
        }

        public String getTgl_lahir() {
            return tgl_lahir;
        }

        public void setTgl_lahir(String tgl_lahir) {
            this.tgl_lahir = tgl_lahir;
        }

        public String getProfile_url() {
            return profile_url;
        }

        public void setProfile_url(String profile_url) {
            this.profile_url = profile_url;
        }

        public String getWallet() {
            return wallet;
        }

        public void setWallet(String wallet) {
            this.wallet = wallet;
        }

        public int getWallet_amount() {
            return wallet_amount;
        }

        public void setWallet_amount(int wallet_amount) {
            this.wallet_amount = wallet_amount;
        }

        public int getPoin() {
            return poin;
        }

        public void setPoin(int poin) {
            this.poin = poin;
        }

        public boolean isIs_need_pin() {
            return is_need_pin;
        }

        public void setIs_need_pin(boolean is_need_pin) {
            this.is_need_pin = is_need_pin;
        }
    }
}
