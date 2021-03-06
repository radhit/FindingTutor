package com.manajemeninformasi.riska.findingtutor.model;

/**
 * Created by Isja on 08/03/2017.
 */

public class CariMuridData {
    private Integer id_pencarian;
    private Integer iduser_pencarian;
    private String nameuser_pencarian;
    private String kelas_pencarian;
    private String pelajaran_pencarian;
    private String alamat_pencarian;
    private String tanggal_pencarian;
    private String hari_pencarian;
    private String jam_pencarian;
    private String biaya_pencarian;
    private String jk_pencarian;
    private String usia_pencarian;
    private Float jarak_pencarian;
    private Integer durasi;

    public CariMuridData(Integer id, Integer id_user, String name, String kelas, String pelajaran, String alamat,
                         String tanggal, String hari, String jam, String biaya, String jeniskelamin, String usia_pencarian, Float jarak, Integer durasi)
    {
        this.id_pencarian = id;
        this.iduser_pencarian = id_user;
        this.nameuser_pencarian = name;
        this.kelas_pencarian = kelas;
        this.pelajaran_pencarian = pelajaran;
        this.alamat_pencarian = alamat;
        this.tanggal_pencarian = tanggal;
        this.hari_pencarian = hari;
        this.jam_pencarian = jam;
        this.biaya_pencarian = biaya;
        this.jk_pencarian = jeniskelamin;
        this.usia_pencarian = usia_pencarian;
        this.jarak_pencarian = jarak;
        this.durasi = durasi;
    }

    public String getUsia_pencarian() {
        return usia_pencarian;
    }

    public void setUsia_pencarian(String usia_pencarian) {
        this.usia_pencarian = usia_pencarian;
    }

    public String getJk_pencarian() {
        return jk_pencarian;
    }

    public void setJk_pencarian(String jk_pencarian) {
        this.jk_pencarian = jk_pencarian;
    }

    public Integer getDurasi() {
        return durasi;
    }

    public void setDurasi(Integer durasi) {
        this.durasi = durasi;
    }

    public Float getJarak_pencarian() {
        return jarak_pencarian;
    }

    public void setJarak_pencarian(Float jarak_pencarian) {
        this.jarak_pencarian = jarak_pencarian;
    }

    public Integer getId_pencarian() {
        return id_pencarian;
    }

    public void setId_pencarian(Integer id_pencarian) {
        this.id_pencarian = id_pencarian;
    }

    public Integer getIduser_pencarian() {
        return iduser_pencarian;
    }

    public void setIduser_pencarian(Integer iduser_pencarian) {
        this.iduser_pencarian = iduser_pencarian;
    }
    //    public String getUsername_pencarian() {
//        return username_pencarian;
//    }
//
//    public void setUsername_pencarian(String username_pencarian) {
//        this.username_pencarian = username_pencarian;
//    }

    public String getNameuser_pencarian() {
        return nameuser_pencarian;
    }

    public void setNameuser_pencarian(String nameuser_pencarian) {
        this.nameuser_pencarian = nameuser_pencarian;
    }

    public String getKelas_pencarian() {
        return kelas_pencarian;
    }

    public void setKelas_pencarian(String kelas_pencarian) {
        this.kelas_pencarian = kelas_pencarian;
    }

    public String getPelajaran_pencarian() {
        return pelajaran_pencarian;
    }

    public void setPelajaran_pencarian(String pelajaran_pencarian) {
        this.pelajaran_pencarian = pelajaran_pencarian;
    }

    public String getAlamat_pencarian() {
        return alamat_pencarian;
    }

    public void setAlamat_pencarian(String alamat_pencarian) {
        this.alamat_pencarian = alamat_pencarian;
    }

    public String getTanggal_pencarian() {
        return tanggal_pencarian;
    }

    public void setTanggal_pencarian(String tanggal_pencarian) {
        this.tanggal_pencarian = tanggal_pencarian;
    }

    public String getHari_pencarian() {
        return hari_pencarian;
    }

    public void setHari_pencarian(String hari_pencarian) {
        this.hari_pencarian = hari_pencarian;
    }

    public String getJam_pencarian() {
        return jam_pencarian;
    }

    public void setJam_pencarian(String jam_pencarian) {
        this.jam_pencarian = jam_pencarian;
    }

    public String getBiaya_pencarian() {
        return biaya_pencarian;
    }

    public void setBiaya_pencarian(String biaya_pencarian) {
        this.biaya_pencarian = biaya_pencarian;
    }
}
