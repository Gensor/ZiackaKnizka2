package com.example.ziackaknizka2;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

class Ziak extends Osoba implements Parcelable {
    Trieda trieda;
    public Ziak(int id, String meno, String priezvisko, Trieda trieda) {
        super(id,meno, priezvisko);
        this.trieda = trieda;
    }


    public Trieda getTrieda() {
        return trieda;
    }

    protected Ziak(Parcel in) {
        super(in);
    }

    @Override
    public int describeContents() {
        return super.describeContents();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    public static final Creator<Ziak> CREATOR = new Creator<Ziak>() {
        @Override
        public Ziak createFromParcel(Parcel in) {
            return new Ziak(in);
        }

        @Override
        public Ziak[] newArray(int size) {
            return new Ziak[size];
        }
    };

}


