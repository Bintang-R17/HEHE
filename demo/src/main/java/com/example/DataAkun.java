package com.example;

import java.time.LocalDate;
import java.util.Objects;

public class DataAkun {
    private String username;
    private String riwayatLogin;
    private LocalDate tanggalPenerimaan;
    private String jenis;
    private double nominal;

    public DataAkun(String username, String riwayatLogin, LocalDate tanggalPenerimaan, String jenis, Double nominal) {
        this.username = username;
        this.riwayatLogin = riwayatLogin;
        this.tanggalPenerimaan = tanggalPenerimaan;
        this.jenis = jenis;
        this.nominal = nominal;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRiwayatLogin() {
        return riwayatLogin;
    }

    public void setRiwayatLogin(String riwayatLogin) {
        this.riwayatLogin = riwayatLogin;
    }

    public LocalDate getTanggalPenerimaan() {
        return tanggalPenerimaan;
    }

    public void setTanggalPenerimaan(LocalDate tanggalPenerimaan) {
        this.tanggalPenerimaan = tanggalPenerimaan;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public double getNominal() {
        return nominal;
    }

    public void setNominal(Double nominal) {
        this.nominal = nominal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataAkun dataAkun = (DataAkun) o;
        return Objects.equals(username, dataAkun.username) &&
                Objects.equals(riwayatLogin, dataAkun.riwayatLogin) &&
                Objects.equals(tanggalPenerimaan, dataAkun.tanggalPenerimaan) &&
                Objects.equals(jenis, dataAkun.jenis) &&
                Objects.equals(nominal, dataAkun.nominal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, riwayatLogin, tanggalPenerimaan, jenis, nominal);
    }

    @Override
    public String toString() {
        return "DataAkun{" +
                "username='" + username + '\'' +
                ", riwayatLogin='" + riwayatLogin + '\'' +
                ", tanggalPenerimaan='" + tanggalPenerimaan + '\'' +
                ", jenis='" + jenis + '\'' +
                ", nominal='" + nominal + '\'' +
                '}';
    }
}
