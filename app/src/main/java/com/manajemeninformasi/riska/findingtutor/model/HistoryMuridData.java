package com.manajemeninformasi.riska.findingtutor.model;

/**
 * Created by Isja on 31/03/2017.
 */

public class HistoryMuridData {
    private Integer id_history;
    private Integer id_transaksi;
    private String nama_tutor;
    private String telp_tutor;
    private String pelajaran;
    private String tanggal;
    private String biaya;
    private String rating;
    private String komentar;

    public HistoryMuridData(Integer id_history, Integer id_transaksi, String nama_tutor ,String telp_tutor, String pelajaran, String tanggal, String biaya, String rating, String komentar)
    {
        this.id_history = id_history;
        this.id_transaksi = id_transaksi;
        this.nama_tutor = nama_tutor;
        this.telp_tutor = telp_tutor;
        this.pelajaran = pelajaran;
        this.tanggal = tanggal;
        this.biaya = biaya;
        this.rating = rating;
        this.komentar = komentar;
    }

    public Integer getId_history() {
        return id_history;
    }

    public void setId_history(Integer id_history) {
        this.id_history = id_history;
    }

    public Integer getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(Integer id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public String getPelajaran() {
        return pelajaran;
    }

    public void setPelajaran(String pelajaran) {
        this.pelajaran = pelajaran;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getNama_tutor() {
        return nama_tutor;
    }

    public void setNama_tutor(String nama_tutor) {
        this.nama_tutor = nama_tutor;
    }

    public String getTelp_tutor() {
        return telp_tutor;
    }

    public void setTelp_tutor(String telp_tutor) {
        this.telp_tutor = telp_tutor;
    }

    public String getBiaya() {
        return biaya;
    }

    public void setBiaya(String biaya) {
        this.biaya = biaya;
    }
}
