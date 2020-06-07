package com.example.ziackaknizka2;

import android.os.Parcel;
import android.os.Parcelable;

public class Osoba implements Parcelable {
    private int id;
    private String meno;
    private String priezvisko;

    public Osoba(int id, String meno,String priezvisko) {
        this.id = id;
        this.meno = meno;
        this.priezvisko = priezvisko;
    }

    protected Osoba(Parcel in) {
        id = in.readInt();
        meno = in.readString();
        priezvisko = in.readString();
    }

    public static final Creator<Osoba> CREATOR = new Creator<Osoba>() {
        @Override
        public Osoba createFromParcel(Parcel in) {
            return new Osoba(in);
        }

        @Override
        public Osoba[] newArray(int size) {
            return new Osoba[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(meno);
        dest.writeString(priezvisko);
    }
}
