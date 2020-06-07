package com.example.ziackaknizka2;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

class Predmet implements Parcelable {
    private int id;
    private String nazov ;
    private String popis = "";
    private String osnova = "";


    protected Predmet(Parcel in) {
        id = in.readInt();
        nazov = in.readString();
        popis = in.readString();
        osnova = in.readString();
    }

    public static final Creator<Predmet> CREATOR = new Creator<Predmet>() {
        @Override
        public Predmet createFromParcel(Parcel in) {
            return new Predmet(in);
        }

        @Override
        public Predmet[] newArray(int size) {
            return new Predmet[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nazov);
        dest.writeString(popis);
        dest.writeString(osnova);
    }

    @NonNull
    @Override
    public String toString() {
        return getNazov();
    }
}
