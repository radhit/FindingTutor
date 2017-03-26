package com.manajemeninformasi.riska.findingtutor.fungsi_keanggotaan;

/**
 * Created by Isja on 26/03/2017.
 */

public class Waktu {
    private  double waktu;

    private  double a = 0;
    private  double b = 4;
    private  double c = 7;
    private  double d = 11;

    public double getWaktu(){
        return waktu;
    }

    public  void setWaktu(double waktu1){
        waktu = waktu1;
    }

    public  double dekat(){
        if(waktu >= a && waktu <= b)
            return 1;
        else if (waktu > b && waktu < c)
            return (c - waktu) / (c - b);
        else
            return 0;

    }

    public  double sedang(){
        if(waktu <= b && waktu >= d)
            return 0;
        else if(waktu > b && waktu < c)
            return (waktu - b) / (c - b);
        else if(waktu >= c && waktu < d)
            return (d - waktu) / (d-c);
        else
            return 0;
    }
    public  double jauh(){
        if(waktu <= c)
            return 0;
        else if(waktu > c && waktu < d)
            return (waktu - c) / (d - c);
        else if(waktu >= d && waktu <= 14)
            return 1;
        else
            return 0;
    }
}
