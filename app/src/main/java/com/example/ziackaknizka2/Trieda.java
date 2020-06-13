package com.example.ziackaknizka2;

import android.os.Parcel;
import android.os.Parcelable;

public class Trieda implements Parcelable {
    private int ID;
    private String nazov;

    public Trieda(int ID, String nazov) {
        this.ID = ID;
        this.nazov = nazov;
    }

    protected Trieda(Parcel in) {
        ID = in.readInt();
        nazov = in.readString();
    }

    public static final Creator<Trieda> CREATOR = new Creator<Trieda>() {
        @Override
        public Trieda createFromParcel(Parcel in) {
            return new Trieda(in);
        }

        @Override
        public Trieda[] newArray(int size) {
            return new Trieda[size];
        }
    };

    public int getID() {
        return ID;
    }

    public String getNazov() {
        return nazov;
    }

    @Override
    public String toString() {
        return getNazov();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(nazov);
    }
}
