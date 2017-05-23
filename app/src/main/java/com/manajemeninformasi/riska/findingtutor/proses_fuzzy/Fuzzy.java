package com.manajemeninformasi.riska.findingtutor.proses_fuzzy;

import com.manajemeninformasi.riska.findingtutor.fungsi_keanggotaan.Harga;
import com.manajemeninformasi.riska.findingtutor.fungsi_keanggotaan.Jarak;
import com.manajemeninformasi.riska.findingtutor.fungsi_keanggotaan.TingkatKesulitan;
import com.manajemeninformasi.riska.findingtutor.fungsi_keanggotaan.Waktu;
/**
 * Created by Isja on 26/03/2017.
 */

public class Fuzzy {
    private static double [] xHarga = new double [27];
    private static double [] yHarga = new double [27];

    private TingkatKesulitan tingkatKesulitan;
    private Jarak jarak;
    private Waktu waktu;

    public static void hitungX(TingkatKesulitan tingkatKesulitan1, Waktu waktu1, Jarak jarak1){
        xHarga[0] = Math.min(tingkatKesulitan1.mudah(), Math.min(waktu1.mendadak(), jarak1.dekat()));
        xHarga[1] = Math.min(tingkatKesulitan1.mudah(), Math.min(waktu1.mendadak(), jarak1.sedang()));
        xHarga[2] = Math.min(tingkatKesulitan1.mudah(), Math.min(waktu1.mendadak(), jarak1.jauh()));
        xHarga[3] = Math.min(tingkatKesulitan1.mudah(), Math.min(waktu1.sedang(), jarak1.dekat()));
        xHarga[4] = Math.min(tingkatKesulitan1.mudah(), Math.min(waktu1.sedang(), jarak1.sedang()));
        xHarga[5] = Math.min(tingkatKesulitan1.mudah(), Math.min(waktu1.sedang(), jarak1.jauh()));
        xHarga[6] = Math.min(tingkatKesulitan1.mudah(), Math.min(waktu1.tdkmendadak(), jarak1.dekat()));
        xHarga[7] = Math.min(tingkatKesulitan1.mudah(), Math.min(waktu1.tdkmendadak(), jarak1.sedang()));
        xHarga[8] = Math.min(tingkatKesulitan1.mudah(), Math.min(waktu1.tdkmendadak(), jarak1.jauh()));
        xHarga[9] = Math.min(tingkatKesulitan1.sedang(), Math.min(waktu1.mendadak(), jarak1.dekat()));
        xHarga[10] = Math.min(tingkatKesulitan1.sedang(), Math.min(waktu1.mendadak(), jarak1.sedang()));
        xHarga[11] = Math.min(tingkatKesulitan1.sedang(), Math.min(waktu1.mendadak(), jarak1.jauh()));
        xHarga[12] = Math.min(tingkatKesulitan1.sedang(), Math.min(waktu1.sedang(), jarak1.dekat()));
        xHarga[13] = Math.min(tingkatKesulitan1.sedang(), Math.min(waktu1.sedang(), jarak1.sedang()));
        xHarga[14] = Math.min(tingkatKesulitan1.sedang(), Math.min(waktu1.sedang(), jarak1.jauh()));
        xHarga[15] = Math.min(tingkatKesulitan1.sedang(), Math.min(waktu1.tdkmendadak(), jarak1.dekat()));
        xHarga[16] = Math.min(tingkatKesulitan1.sedang(), Math.min(waktu1.tdkmendadak(), jarak1.sedang()));
        xHarga[17] = Math.min(tingkatKesulitan1.sedang(), Math.min(waktu1.tdkmendadak(), jarak1.jauh()));
        xHarga[18] = Math.min(tingkatKesulitan1.sulit(), Math.min(waktu1.mendadak(), jarak1.dekat()));
        xHarga[19] = Math.min(tingkatKesulitan1.sulit(), Math.min(waktu1.mendadak(), jarak1.sedang()));
        xHarga[20] = Math.min(tingkatKesulitan1.sulit(), Math.min(waktu1.mendadak(), jarak1.jauh()));
        xHarga[21] = Math.min(tingkatKesulitan1.sulit(), Math.min(waktu1.sedang(), jarak1.dekat()));
        xHarga[22] = Math.min(tingkatKesulitan1.sulit(), Math.min(waktu1.sedang(), jarak1.sedang()));
        xHarga[23] = Math.min(tingkatKesulitan1.sulit(), Math.min(waktu1.sedang(), jarak1.jauh()));
        xHarga[24] = Math.min(tingkatKesulitan1.sulit(), Math.min(waktu1.tdkmendadak(), jarak1.dekat()));
        xHarga[25] = Math.min(tingkatKesulitan1.sulit(), Math.min(waktu1.tdkmendadak(), jarak1.sedang()));
        xHarga[26] = Math.min(tingkatKesulitan1.sulit(), Math.min(waktu1.tdkmendadak(), jarak1.jauh()));
    }

    public static void hitungY(){
        yHarga[0] = Harga.murah(xHarga[0]);
        yHarga[1] = Harga.sedang(xHarga[1]);
        yHarga[2] = Harga.sedang(xHarga[2]);
        yHarga[3] = Harga.murah(xHarga[3]);
        yHarga[4] = Harga.sedang(xHarga[4]);
        yHarga[5] = Harga.sedang(xHarga[5]);
        yHarga[6] = Harga.murah(xHarga[6]);
        yHarga[7] = Harga.murah(xHarga[7]);
        yHarga[8] = Harga.sedang(xHarga[8]);
        yHarga[9] = Harga.sedang(xHarga[9]);
        yHarga[10] = Harga.sedang(xHarga[10]);
        yHarga[11] = Harga.mahal(xHarga[11]);
        yHarga[12] = Harga.sedang(xHarga[12]);
        yHarga[13] = Harga.sedang(xHarga[13]);
        yHarga[14] = Harga.mahal(xHarga[14]);
        yHarga[15] = Harga.murah(xHarga[15]);
        yHarga[16] = Harga.sedang(xHarga[16]);
        yHarga[17] = Harga.sedang(xHarga[17]);
        yHarga[18] = Harga.sedang(xHarga[18]);
        yHarga[19] = Harga.mahal(xHarga[19]);
        yHarga[20] = Harga.mahal(xHarga[20]);
        yHarga[21] = Harga.sedang(xHarga[21]);
        yHarga[22] = Harga.mahal(xHarga[22]);
        yHarga[23] = Harga.mahal(xHarga[23]);
        yHarga[24] = Harga.sedang(xHarga[24]);
        yHarga[25] = Harga.sedang(xHarga[25]);
        yHarga[26] = Harga.mahal(xHarga[26]);
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
