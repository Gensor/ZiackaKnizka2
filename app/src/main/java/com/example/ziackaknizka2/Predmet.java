package com.example.ziackaknizka2;


class Predmet {
    private int id;
    private String nazov ;
    private String popis = "";
    private String osnova = "";



    public Predmet(int id,String nazov) {
        this.id = id;
        this.nazov = nazov;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Predmet(int id, String nazov, String popis) {
        this.id = id;
        this.nazov = nazov;
        this.popis = popis;
    }

    public Predmet(int id, String nazov, String popis, String osnova) {
        this.id = id;
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
