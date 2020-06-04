package com.example.ziackaknizka2;

public class Osoba {
    private int id;
    private String meno;
    private String priezvisko;

    public Osoba(int id, String meno,String priezvisko) {
        this.id = id;
        this.meno = meno;
        this.priezvisko = priezvisko;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMeno() {
        return meno;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    public String getPriezvisko() {
        return priezvisko;
    }

    public void setPriezvisko(String priezvisko) {
        this.priezvisko = priezvisko;
    }

    @Override
    public String toString() {
        return meno + " " + priezvisko;
    }
}
