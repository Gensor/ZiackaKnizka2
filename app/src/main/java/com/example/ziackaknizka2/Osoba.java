package com.example.ziackaknizka2;

public abstract class Osoba {
    private String meno;
    private String priezvisko;

    public Osoba(String meno,String priezvisko) {
        this.meno = meno;
        this.priezvisko = priezvisko;
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
