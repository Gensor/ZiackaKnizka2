package com.example.ziackaknizka2;

public class Znamka {
    int id;
    Ziak ziak;
    UcitelovPredmet predmet;
    String znamka;

    public Znamka(int id, Ziak ziak, UcitelovPredmet predmet, String znamka) {
        this.id = id;
        this.ziak = ziak;
        this.predmet = predmet;
        this.znamka = znamka;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ziak getZiak() {
        return ziak;
    }

    public void setZiak(Ziak ziak) {
        this.ziak = ziak;
    }

    public UcitelovPredmet getPredmet() {
        return predmet;
    }

    public void setPredmet(UcitelovPredmet predmet) {
        this.predmet = predmet;
    }

    public String getZnamka() {
        return znamka;
    }

    public void setZnamka(String znamka) {
        this.znamka = znamka;
    }

    @Override
    public String toString() {
        return getZnamka();
    }
}
