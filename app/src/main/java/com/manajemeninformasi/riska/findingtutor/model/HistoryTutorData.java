package com.manajemeninformasi.riska.findingtutor.model;

/**
 * Created by Isja on 31/03/2017.
 */

public class HistoryTutorData {
    private Integer id_history;
    private String tanggal;
    private String rating;
    private String komentar;

    public HistoryTutorData(Integer id_history, String tanggal, String rating, String komentar)
    {
        this.id_history = id_history;
        this.tanggal = tanggal;
        this.rating = rating;
        this.komentar = komentar;
    }

    public Integer getId_history() {
        return id_history;
    }

    public void setId_history(Integer id_history) {
        this.id_history = id_history;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
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
}
