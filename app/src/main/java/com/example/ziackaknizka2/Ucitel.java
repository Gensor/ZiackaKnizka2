package com.example.ziackaknizka2;

import java.util.ArrayList;

public class Ucitel extends Osoba {
    private ArrayList <UcitelovPredmet> predmety;

     Ucitel(int id, String meno, String priezvisko) {
        super(id,meno,priezvisko);
        predmety = new ArrayList<>();

    }



    public void addPredmet(UcitelovPredmet predmet){
        predmety.add(predmet);
    }

    public void removePredmet(int pozicia){
        predmety.remove(pozicia);
    }

    public ArrayList<UcitelovPredmet> getPredmety(){
        return predmety;
    }
}
