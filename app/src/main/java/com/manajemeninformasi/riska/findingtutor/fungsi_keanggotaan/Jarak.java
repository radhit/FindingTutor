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

        if(jarak >= a && jarak <= b)
            return 1;
        else if (jarak > b && jarak < c)
            return (c - jarak) / (c - b);
        else
            return 0;

    }

    public  double sedang(){

        if(jarak <= b && jarak >= d)
            return 0;
        else if(jarak > b && jarak < c)
            return (jarak - b) / (c - b);
        else if(jarak >= c && jarak < d)
            return (d - jarak) / (d-c);
        else
            return 0;
    }
    public  double jauh(){
        if(jarak <= c)
            return 0;
        else if(jarak > c && jarak < d)
            return (jarak - c) / (d - c);
        else if(jarak >= d && jarak <= 35000)
            return 1;
        else
            return 0;
    }
}
