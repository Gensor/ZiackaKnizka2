package com.example.ziackaknizka2;


import android.os.Parcel;
import android.os.Parcelable;

public class Ucitel extends Osoba implements Parcelable {

     Ucitel(int id, String meno, String priezvisko) {
        super(id,meno,priezvisko);

    }

    public Ucitel(Parcel in) {
        super(in);
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
         super.writeToParcel(dest,flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Ucitel> CREATOR = new Creator<Ucitel>() {
        @Override
        public Ucitel createFromParcel(Parcel in) {
            return new Ucitel(in);
        }

        @Override
        public Ucitel[] newArray(int size) {
            return new Ucitel[size];
        }
    };




    }

