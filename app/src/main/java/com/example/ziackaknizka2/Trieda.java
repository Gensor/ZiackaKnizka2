package com.example.ziackaknizka2;

public class Trieda {
    private int ID;
    private String nazov;

    public Trieda(int ID, String nazov) {
        this.ID = ID;
        this.nazov = nazov;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    @Override
    public String toString() {
        return getNazov();
    }
}
