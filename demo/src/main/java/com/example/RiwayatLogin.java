package com.example;

public class RiwayatLogin {
    private String username;
    private String nomorhp;
    private String waktuLogin;
    private String status;

    public RiwayatLogin(String username, String nomorhp, String waktuLogin, String status) {
        this.username = username;
        this.nomorhp = nomorhp;
        this.waktuLogin = waktuLogin;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public String getNomorhp() {
        return nomorhp;
    }

    public String getWaktuLogin() {
        return waktuLogin;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return username + "," + nomorhp + "," + waktuLogin + "," + status;
    }
}
