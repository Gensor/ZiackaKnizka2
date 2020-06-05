package com.example.ziackaknizka2;

import java.util.ArrayList;

public class UcitelovPredmet  {
    private int id;
    private Ucitel ucitel;
    private Predmet predmet;
    private Trieda trieda;
    private ArrayList<Ziak> ziaci;

    public UcitelovPredmet(int id, Ucitel ucitel, Predmet predmet,Trieda trieda) {
        this.id = id;
        this.ucitel = ucitel;
        this.predmet = predmet;
        this.trieda = trieda;
        this.ziaci = new ArrayList<>();
    }

    public void addZiak(Ziak ziak){
        ziaci.add(ziak);
    }

    @Override
    public String toString() {
        return trieda.getNazov()+": "+predmet.getNazov();
    }
}
