package com.example.ziackaknizka2;

import java.util.ArrayList;

class Ziak extends Osoba{
    private ArrayList<Double> hodnotenia;

    public Ziak(int id, String meno, String priezvisko) {
        super(id,meno, priezvisko);
        this.hodnotenia = new ArrayList<>();
    }

    public void addHodnotenie(Double hodnotenie){
        hodnotenia.add(hodnotenie);
    }

    public ArrayList<Double> getHodnotenia(){
        return hodnotenia;
    }

    public void setHodnotenie(int pozicia, double hodnotenie){
        hodnotenia.set(pozicia,hodnotenie);
    }

    public void removeHodnotenie(int pozicia){
        hodnotenia.remove(pozicia);
    }

    public EZnamka getHodnoteniePredmetu(){
        double sucet = 0;
        for (double zapocet:hodnotenia) {
            sucet += zapocet;
        }

        if(sucet>=92){
            return EZnamka.A;
        }else if (sucet>=84){
            return EZnamka.B;
        }else if (sucet>=76){
            return EZnamka.C;
        }else if (sucet>=68){
            return EZnamka.D;
        }else if (sucet>=60){
            return EZnamka.E;
        }else{
            return EZnamka.FX;
        }

    }

}
