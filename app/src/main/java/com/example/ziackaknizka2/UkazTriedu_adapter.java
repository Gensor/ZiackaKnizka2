package com.example.ziackaknizka2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class UkazTriedu_adapter extends ArrayAdapter {
    private UcitelovPredmet predmet;

    public UkazTriedu_adapter(@NonNull Context context, ArrayList<Ziak> ziaci, UcitelovPredmet predmet) {
        super(context, R.layout.activity_ukaztriedu_riadok,ziaci);
        this.predmet = predmet;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myInflator = LayoutInflater.from(getContext());
        View customView = myInflator.inflate(R.layout.activity_ukaztriedu_riadok,parent,false);
        final DBhelper databaza = new DBhelper(parent.getContext());

        final Ziak ziak =(Ziak) getItem(position);

        TextView meno = customView.findViewById(R.id.textView_ukaztriedu_riadok_meno);
        TextView znamka = customView.findViewById(R.id.textView_ukaztriedu_riadok_znamka);

        if(ziak!=null) {
            meno.setText(ziak.toString());
            if (databaza.hasZnamka(predmet, ziak)) {
                znamka.setText(databaza.getZnamka(ziak, predmet).getZnamka());
            }
        }
        databaza.close();
        return customView;
    }
}
