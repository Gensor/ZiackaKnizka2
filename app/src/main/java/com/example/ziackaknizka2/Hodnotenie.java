package com.example.ziackaknizka2;

import android.os.Parcel;
import android.os.Parcelable;

public class Hodnotenie implements Parcelable {

    int id;
    String nazov;
    int body;
    UcitelovPredmet predmet;
    Ziak ziak;

    public Hodnotenie(int id, String nazov, int body, UcitelovPredmet predmet, Ziak ziak) {
        this.id = id;
        this.nazov = nazov;
        this.body = body;
        this.predmet = predmet;
        this.ziak = ziak;
    }


    protected Hodnotenie(Parcel in) {
        id = in.readInt();
        nazov = in.readString();
        body = in.readInt();
        predmet = in.readParcelable(UcitelovPredmet.class.getClassLoader());
        ziak = in.readParcelable(Ziak.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nazov);
        dest.writeInt(body);
        dest.writeParcelable(predmet, flags);
        dest.writeParcelable(ziak, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Hodnotenie> CREATOR = new Creator<Hodnotenie>() {
        @Override
        public Hodnotenie createFromParcel(Parcel in) {
            return new Hodnotenie(in);
        }

        @Override
        public Hodnotenie[] newArray(int size) {
            return new Hodnotenie[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public int getBody() {
        return body;
    }

    public void setBody(int body) {
        this.body = body;
    }

    public UcitelovPredmet getPredmet() {
        return predmet;
    }

    public void setPredmet(UcitelovPredmet predmet) {
        this.predmet = predmet;
    }

    public Ziak getZiak() {
        return ziak;
    }

    public void setZiak(Ziak ziak) {
        this.ziak = ziak;
    }

    @Override
    public String toString() {
        return "Hodnotenie{" +
                "id=" + id +
                ", nazov='" + nazov + '\'' +
                ", body=" + body +
                ", predmet=" + predmet +
                ", ziak=" + ziak +
                '}';
    }
}
