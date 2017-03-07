package com.manajemeninformasi.riska.findingtutor.model;

/**
 * Created by Isja on 07/03/2017.
 */

public class KeahlianTutorData {
    private Integer id_keahlian;
    private String username_keahlian;
    private String kelas_keahlian;
    private String pelajaran_keahlian;
    private String keterbatasanHari_keahlian;
    private String jam_keahlian;

    public KeahlianTutorData(Integer id, String username, String kelas, String pelajaran, String keterbatasanHari, String jam)
    {
        this.id_keahlian = id;
        this.username_keahlian = username;
        this.kelas_keahlian = kelas;
        this.pelajaran_keahlian = pelajaran;
        this.keterbatasanHari_keahlian = keterbatasanHari;
        this.jam_keahlian = jam;
    }

    public Integer getId_keahlian() {
        return id_keahlian;
    }

    public void setId_keahlian(Integer id_keahlian) {
        this.id_keahlian = id_keahlian;
    }

    public String getUsername_keahlian() {
        return username_keahlian;
    }

    public void setUsername_keahlian(String username_keahlian) {
        this.username_keahlian = username_keahlian;
    }

    public String getKelas_keahlian() {
        return kelas_keahlian;
    }

    public void setKelas_keahlian(String kelas_keahlian) {
        this.kelas_keahlian = kelas_keahlian;
    }

    public String getPelajaran_keahlian() {
        return pelajaran_keahlian;
    }

    public void setPelajaran_keahlian(String pelajaran_keahlian) {
        this.pelajaran_keahlian = pelajaran_keahlian;
    }

    public String getKeterbatasanHari_keahlian() {
        return keterbatasanHari_keahlian;
    }

    public void setKeterbatasanHari_keahlian(String keterbatasanHari_keahlian) {
        this.keterbatasanHari_keahlian = keterbatasanHari_keahlian;
    }

    public String getJam_keahlian() {
        return jam_keahlian;
    }

    public void setJam_keahlian(String jam_keahlian) {
        this.jam_keahlian = jam_keahlian;
    }
}
