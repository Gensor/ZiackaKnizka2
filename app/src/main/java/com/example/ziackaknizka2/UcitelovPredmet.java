package com.example.ziackaknizka2;

import java.util.ArrayList;

public class UcitelovPredmet  {

    private Predmet predmet;
    private ArrayList<Ziak> ziaci;

    public UcitelovPredmet(Predmet predmet) {

        this.predmet = predmet;
        this.ziaci = new ArrayList<>();
    }

    public void addZiak(Ziak ziak){
        ziaci.add(ziak);
    }

    @Override
    public String toString() {
        return predmet.getNazov();
    }
}
