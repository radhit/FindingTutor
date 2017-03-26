package com.manajemeninformasi.riska.findingtutor.fungsi_keanggotaan;

/**
 * Created by Isja on 26/03/2017.
 */

public class TingkatKesulitan {
    private double tingkatkesulitan;

    private double a = 1;
    private double b = 2;
    private double c = 6;
    private double d = 11;

    public double getTingkatKesulitan(){
        return tingkatkesulitan;
    }

    public void setTingkatKesulitan(double tingkatkesulitan1){
        tingkatkesulitan = tingkatkesulitan1;
    }

    public double mudah(){
        if(tingkatkesulitan >= a && tingkatkesulitan <= b)
            return 1;
        else if (tingkatkesulitan > b && tingkatkesulitan < c)
            return (c - tingkatkesulitan) / (c - b);
        else
            return 0;

    }

    public  double sedang(){
        if(tingkatkesulitan <= b && tingkatkesulitan >= d)
            return 0;
        else if(tingkatkesulitan > b && tingkatkesulitan < c)
            return (tingkatkesulitan - b) / (c - b);
        else if(tingkatkesulitan >= c && tingkatkesulitan < d)
            return (d - tingkatkesulitan) / (d-c);
        else
            return 0;
    }
    public  double sulit(){
        if(tingkatkesulitan <= c)
            return 0;
        else if(tingkatkesulitan > c && tingkatkesulitan < d)
            return (tingkatkesulitan - c) / (d - c);
        else if(tingkatkesulitan >= d && tingkatkesulitan <= 13)
            return 1;
        else
            return 0;
    }
}
