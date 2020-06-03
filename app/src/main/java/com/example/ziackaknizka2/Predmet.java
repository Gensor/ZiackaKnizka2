package com.example.ziackaknizka2;


class Predmet {

    private String nazov ;
    private String popis = "";
    private String osnova = "";



    public Predmet(String nazov) {
        this.nazov = nazov;
    }

    public Predmet(String nazov, String popis) {
        this.nazov = nazov;
        this.popis = popis;
    }

    public Predmet(String nazov, String popis, String osnova) {
        this.nazov = nazov;
        this.popis = popis;
        this.osnova = osnova;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public String getOsnova() {
        return osnova;
    }

    public void setOsnova(String osnova) {
        this.osnova = osnova;
    }
}
