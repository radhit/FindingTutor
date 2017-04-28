package com.manajemeninformasi.riska.findingtutor.model;

/**
 * Created by Isja on 07/03/2017.
 */

public class KeahlianTutorData {
    private Integer id_keahlian;
    private String username_keahlian;
    private String kelas_keahlian;
    private String pelajaran_keahlian;

    public KeahlianTutorData(Integer id, String kelas, String pelajaran)
    {
        this.id_keahlian = id;
        this.kelas_keahlian = kelas;
        this.pelajaran_keahlian = pelajaran;
    }

    public Integer getId_keahlian() {
        return id_keahlian;
    }

    public void setId_keahlian(Integer id_keahlian) {
        this.id_keahlian = id_keahlian;
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

}
