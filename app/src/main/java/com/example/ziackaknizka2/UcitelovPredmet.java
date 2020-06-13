package com.example.ziackaknizka2;

import android.os.Parcel;
import android.os.Parcelable;

public class UcitelovPredmet  implements Parcelable {
    private int id;
    private Ucitel ucitel;
    private Predmet predmet;
    private Trieda trieda;

    public UcitelovPredmet(int id, Ucitel ucitel, Predmet predmet,Trieda trieda) {
        this.id = id;
        this.ucitel = ucitel;
        this.predmet = predmet;
        this.trieda = trieda;
    }

    protected UcitelovPredmet(Parcel in) {
        id = in.readInt();
        ucitel = in.readParcelable(Ucitel.class.getClassLoader());
        predmet = in.readParcelable(Predmet.class.getClassLoader());
        trieda = in.readParcelable(Trieda.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(ucitel, flags);
        dest.writeParcelable(predmet, flags);
        dest.writeParcelable(trieda, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UcitelovPredmet> CREATOR = new Creator<UcitelovPredmet>() {
        @Override
        public UcitelovPredmet createFromParcel(Parcel in) {
            return new UcitelovPredmet(in);
        }

        @Override
        public UcitelovPredmet[] newArray(int size) {
            return new UcitelovPredmet[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ucitel getUcitel() {
        return ucitel;
    }

    public Predmet getPredmet() {
        return predmet;
    }

    public void setPredmet(Predmet predmet) {
        this.predmet = predmet;
    }

    public Trieda getTrieda() {
        return trieda;
    }

    @Override
    public String toString() {
        return trieda.getNazov()+": "+predmet.getNazov();
    }

}
