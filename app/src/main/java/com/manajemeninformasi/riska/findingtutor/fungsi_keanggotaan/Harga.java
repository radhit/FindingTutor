package com.manajemeninformasi.riska.findingtutor.fungsi_keanggotaan;

/**
 * Created by Isja on 26/03/2017.
 */

public class Harga {
    private static double harga;

    public static double getHarga(){
        return harga;
    }

    public static void setHarga(double harga){
        Harga.harga = harga;
    }

    public static double murah(){

        if(harga >= 35000 && harga <= 45000)
            return 1;
        else if (harga > 45000 && harga < 60000)
            return (60000 - harga) / 15000;
        else
            return 0;

    }

    public static double sedang(){
        if(harga >= 60000 && harga <= 80000)
            return 1;
        else if(harga > 45000 && harga < 60000)
            return (harga - 45000) / 15000;
        else if(harga <= 45000 && harga > 80000)
            return 0;
        else
            return 0;
    }
    public static double mahal(){
        if(harga <= 80000 && harga > 100000)
            return 0;
        else if(harga > 80000 && harga < 90000)
            return (harga - 80000) / 10000;
        else if(harga >= 9000 && harga <= 100000)
            return 1;
        else
            return 0;
    }

    public static double murah(double alfa){

        return(60000 - (alfa*15000));
    }

    public static double sedang(double alfa){

        return(45000 + (alfa*15000));
    }

    public static double mahal(double alfa){
        return(80000 + (alfa*10000));

    }
}
