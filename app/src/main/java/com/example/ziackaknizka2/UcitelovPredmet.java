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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ucitel getUcitel() {
        return ucitel;
    }

    public void setUcitel(Ucitel ucitel) {
        this.ucitel = ucitel;
    }

    public Predmet getPredmet() {
        return predmet;
    }

    public void setPredmet(Predmet predmet) {
        this.predmet = predmet;
    }

    public Trieda getTrieda() {
        return trieda;
    }

    public void setTrieda(Trieda trieda) {
        this.trieda = trieda;
    }

    public ArrayList<Ziak> getZiaci() {
        return ziaci;
    }

    public void setZiaci(ArrayList<Ziak> ziaci) {
        this.ziaci = ziaci;
    }

    @Override
    public String toString() {
        return trieda.getNazov()+": "+predmet.getNazov();
    }
}
