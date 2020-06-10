package com.example.ziackaknizka2;

public class Hodnotenie {

    int id;
    String nazov;
    int body;
    UcitelovPredmet predmet;
    Ziak ziak;

    public Hodnotenie(int id, String nazov, int body, UcitelovPredmet predmet, Ziak ziak) {
        this.id = id;
        this.nazov = nazov;
        this.body = body;
        this.predmet = predmet;
        this.ziak = ziak;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public int getBody() {
        return body;
    }

    public void setBody(int body) {
        this.body = body;
    }

    public UcitelovPredmet getPredmet() {
        return predmet;
    }

    public void setPredmet(UcitelovPredmet predmet) {
        this.predmet = predmet;
    }

    public Ziak getZiak() {
        return ziak;
    }

    public void setZiak(Ziak ziak) {
        this.ziak = ziak;
    }
}
