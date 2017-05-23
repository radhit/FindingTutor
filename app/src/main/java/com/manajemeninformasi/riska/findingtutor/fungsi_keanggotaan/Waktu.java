package com.manajemeninformasi.riska.findingtutor.fungsi_keanggotaan;

/**
 * Created by Isja on 26/03/2017.
 */

public class Waktu {
    private  double waktu;

    public double getWaktu(){
        return waktu;
    }

    public  void setWaktu(double waktu1){
        waktu = waktu1;
    }

    public  double mendadak(){
        if(waktu >= 0 && waktu <= 3)
            return 1;
        else if (waktu > 3 && waktu < 5)
            return (5 - waktu) / 2;
        else
            return 0;

    }

    public  double sedang(){
        if(waktu <= 3 && waktu > 7)
            return 0;
        else if(waktu > 3 && waktu <= 5)
            return (waktu - 3) / 2;
        else if(waktu > 5 && waktu <= 7)
            return 1;
        else
            return 0;
    }
    public  double tdkmendadak(){
        if(waktu <= 7)
            return 0;
        else if(waktu > 7 && waktu < 10)
            return (waktu - 6) / 2;
        else if(waktu >= 10 && waktu <= 14)
            return 1;
        else
            return 0;
    }
}
