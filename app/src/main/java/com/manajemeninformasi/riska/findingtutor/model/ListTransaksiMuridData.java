package com.manajemeninformasi.riska.findingtutor.model;

/**
 * Created by Isja on 18/04/2017.
 */

public class ListTransaksiMuridData {
    private Integer id_transaksi;
    private Integer id_pencariantutor;
    private String pelajaran;
    private String tutor;
    public ListTransaksiMuridData(Integer id_transaksi, Integer id_pencariantutor, String pelajaran, String tutor)
    {
        this.id_transaksi = id_transaksi;
        this.id_pencariantutor = id_pencariantutor;
        this.pelajaran = pelajaran;
        this.tutor = tutor;
    }

    public Integer getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(Integer id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public Integer getId_pencariantutor() {
        return id_pencariantutor;
    }

    public void setId_pencariantutor(Integer id_pencariantutor) {
        this.id_pencariantutor = id_pencariantutor;
    }

    public String getPelajaran() {
        return pelajaran;
    }

    public void setPelajaran(String pelajaran) {
        this.pelajaran = pelajaran;
    }

    public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }
}
