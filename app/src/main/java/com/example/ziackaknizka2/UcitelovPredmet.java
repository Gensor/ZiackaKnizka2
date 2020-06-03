package com.example.ziackaknizka2;

import java.util.ArrayList;

public class UcitelovPredmet  {
    private Ucitel ucitel;
    private Predmet predmet;
    private ArrayList<Ziak> ziaci;

    public UcitelovPredmet(Ucitel ucitel, Predmet predmet) {
        this.ucitel = ucitel;
        this.predmet = predmet;
        this.ziaci = new ArrayList<>();
    }

    public void addZiak(Ziak ziak){
        ziaci.add(ziak);
    }

}
