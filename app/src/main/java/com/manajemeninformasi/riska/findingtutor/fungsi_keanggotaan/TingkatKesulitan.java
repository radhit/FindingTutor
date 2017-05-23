package com.manajemeninformasi.riska.findingtutor.fungsi_keanggotaan;

/**
 * Created by Isja on 26/03/2017.
 */

public class TingkatKesulitan {
    private double tingkatkesulitan;

    public double getTingkatKesulitan(){
        return tingkatkesulitan;
    }

    public void setTingkatKesulitan(double tingkatkesulitan1){
        tingkatkesulitan = tingkatkesulitan1;
    }

    public double mudah(){
        if(tingkatkesulitan >= 1 && tingkatkesulitan <= 4)
            return 1;
        else if (tingkatkesulitan > 4 && tingkatkesulitan < 6)
            return (6 - tingkatkesulitan) / 2;
        else
            return 0;
    }

    public  double sedang(){
        if(tingkatkesulitan <= 4 && tingkatkesulitan > 9)
            return 0;
        else if(tingkatkesulitan > 4 && tingkatkesulitan <= 6)
            return (tingkatkesulitan - 4) / 2;
        else if(tingkatkesulitan > 6 && tingkatkesulitan <= 9)
            return 1;
        else
            return 0;
    }
    public  double sulit(){
        if(tingkatkesulitan <= 9 && tingkatkesulitan > 13)
            return 0;
        else if(tingkatkesulitan > 9 && tingkatkesulitan <= 12)
            return (tingkatkesulitan - 9) / 3;
        else if(tingkatkesulitan > 12 && tingkatkesulitan <= 13)
            return 1;
        else
            return 0;
    }
}
