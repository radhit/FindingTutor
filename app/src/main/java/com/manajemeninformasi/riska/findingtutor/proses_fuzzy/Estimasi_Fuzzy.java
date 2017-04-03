package com.manajemeninformasi.riska.findingtutor.proses_fuzzy;

import com.manajemeninformasi.riska.findingtutor.fungsi_keanggotaan.Harga;
import com.manajemeninformasi.riska.findingtutor.fungsi_keanggotaan.TingkatKesulitan;
import com.manajemeninformasi.riska.findingtutor.fungsi_keanggotaan.Waktu;

/**
 * Created by Isja on 03/04/2017.
 */

public class Estimasi_Fuzzy {
    private static double [] xHarga = new double [27];
    private static double [] yHarga = new double [27];

    private TingkatKesulitan tingkatKesulitan;
    private Waktu waktu;

    public static void hitungX(TingkatKesulitan tingkatKesulitan1, Waktu waktu1){
        xHarga[0] = Math.min(tingkatKesulitan1.mudah(), waktu1.jauh());
        xHarga[1] = Math.min(tingkatKesulitan1.mudah(), waktu1.sedang());
        xHarga[2] = Math.min(tingkatKesulitan1.mudah(), waktu1.dekat());
        xHarga[3] = Math.min(tingkatKesulitan1.sedang(), waktu1.jauh());
        xHarga[4] = Math.min(tingkatKesulitan1.sedang(), waktu1.sedang());
        xHarga[5] = Math.min(tingkatKesulitan1.sedang(), waktu1.dekat());
        xHarga[6] = Math.min(tingkatKesulitan1.sulit(), waktu1.jauh());
        xHarga[7] = Math.min(tingkatKesulitan1.sulit(), waktu1.sedang());
        xHarga[8] = Math.min(tingkatKesulitan1.sulit(), waktu1.dekat());
    }

    public static void hitungY(){
        yHarga[0] = Harga.murah(xHarga[0]);
        yHarga[1] = Harga.murah(xHarga[1]);
        yHarga[2] = Harga.sedang(xHarga[2]);
        yHarga[3] = Harga.murah(xHarga[3]);
        yHarga[4] = Harga.sedang(xHarga[4]);
        yHarga[5] = Harga.sedang(xHarga[5]);
        yHarga[6] = Harga.sedang(xHarga[6]);
        yHarga[7] = Harga.sedang(xHarga[7]);
        yHarga[8] = Harga.mahal(xHarga[8]);
    }

    public static double defuzzyfikasi(){
        double atas = 0, bawah = 0;
        for(int i = 0 ; i < 27 ; i++){
            System.out.println("x ke-"+i+ " : " + xHarga[i]);
            System.out.println("y ke-"+i+ " : " + yHarga[i]);
            atas += (xHarga[i] * yHarga[i]);
            bawah += xHarga[i];

        }
        System.out.println(atas/bawah);
        return(atas/bawah);
    }
}
