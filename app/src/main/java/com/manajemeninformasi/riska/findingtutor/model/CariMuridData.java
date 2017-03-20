package com.manajemeninformasi.riska.findingtutor.model;

/**
 * Created by Isja on 08/03/2017.
 */

public class CariMuridData {
    private Integer id_pencarian;
    private String username_pencarian;
    private String nameuser_pencarian;
    private String kelas_pencarian;
    private String pelajaran_pencarian;
    private String alamat_pencarian;
    private String tanggal_pencarian;
    private String hari_pencarian;
    private String jam_pencarian;
    private String biaya_pencarian;
    private Float jarak_pencarian;

    public CariMuridData(Integer id, String username, String name, String kelas, String pelajaran, String alamat,
                         String tanggal, String hari, String jam, String biaya, Float jarak)
    {
        this.id_pencarian = id;
        this.username_pencarian = username;
        this.nameuser_pencarian = name;
        this.kelas_pencarian = kelas;
        this.pelajaran_pencarian = pelajaran;
        this.alamat_pencarian = alamat;
        this.tanggal_pencarian = tanggal;
        this.hari_pencarian = hari;
        this.jam_pencarian = jam;
        this.biaya_pencarian = biaya;
        this.jarak_pencarian = jarak;
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

    public String getUsername_pencarian() {
        return username_pencarian;
    }

    public void setUsername_pencarian(String username_pencarian) {
        this.username_pencarian = username_pencarian;
    }

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
