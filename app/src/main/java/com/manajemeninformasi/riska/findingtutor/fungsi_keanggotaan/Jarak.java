package com.manajemeninformasi.riska.findingtutor.fungsi_keanggotaan;

/**
 * Created by Isja on 26/03/2017.
 */

public class Jarak {
    private  double jarak;

    private  double a = 0;
    private  double b = 3000;
    private  double c = 6000;
    private  double d = 9000;

    public double getjarak(){
        return jarak;
    }

    public  void setJarak(double jarak1){
        jarak = jarak1;
    }

    public  double dekat(){

        if(jarak >= 0 && jarak <= 3000)
            return 1;
        else if (jarak > 3000 && jarak < 5000)
            return (5000 - jarak) / 2000;
        else
            return 0;

    }

    public  double sedang(){

        if(jarak <= 3000 && jarak > 7500)
            return 0;
        else if(jarak > 3000 && jarak <= 5000)
            return (jarak - 3000) / 2000;
        else if(jarak > 5000 && jarak <= 7500)
            return 1;
        else
            return 0;
    }
    public  double jauh(){
        if(jarak <= 7500 && jarak > 35000)
            return 0;
        else if(jarak > 7500 && jarak <= 9000)
            return (jarak - 7500) / 1500;
        else if(jarak > 9000 && jarak <= 35000)
            return 1;
        else
            return 0;
    }
}
