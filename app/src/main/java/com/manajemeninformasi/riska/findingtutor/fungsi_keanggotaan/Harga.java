package com.manajemeninformasi.riska.findingtutor.fungsi_keanggotaan;

/**
 * Created by Isja on 26/03/2017.
 */

public class Harga {
    private static double harga;

    private static double a = 35000;
    private static double b = 45000;
    private static double c = 60000;
    private static double d = 80000;
    private static double e = 90000;

    public static double getHarga(){
        return harga;
    }

    public static void setHarga(double harga){
        Harga.harga = harga;
    }

    public static double murah(){

        if(harga >= a && harga <= b)
            return 1;
        else if (harga > b && harga < c)
            return (c - harga) / (c - b);
        else
            return 0;

    }

    public static double sedang(){
        if(harga <= b && harga >= d)
            return 0;
        else if(harga > b && harga < c)
            return (harga - b) / (c - b);
        else
            return 0;
    }
    public static double mahal(){
        if(harga <= d && harga > 100000)
            return 0;
        else if(harga > d && harga < e)
            return (harga - d) / (e - d);
        else if(harga >= e && harga <= 100000)
            return 1;
        else
            return 0;
    }

    public static double murah(double alfa){
        return(c - (alfa*(c - b)));
    }

    public static double sedang(double alfa){
        return(b + (alfa*(c - b)));
    }

    public static double mahal(double alfa){
        return(d + (alfa*(e - d)));

    }
}
