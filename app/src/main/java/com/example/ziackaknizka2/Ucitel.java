package com.example.ziackaknizka2;

import java.util.ArrayList;

public class Ucitel extends Osoba {
    private ArrayList <Predmet> predmety;

    public Ucitel(String meno, String priezvisko) {
        super(meno,priezvisko);
        predmety = new ArrayList<>();

    }

    public void addPredmet(Predmet predmet){
        predmety.add(predmet);
    }

    public void removePredmet(int pozicia){
        predmety.remove(pozicia);
    }
}
