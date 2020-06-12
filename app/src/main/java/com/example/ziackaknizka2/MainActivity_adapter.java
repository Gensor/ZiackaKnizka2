package com.example.ziackaknizka2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MainActivity_adapter extends ArrayAdapter {

    ArrayList<UcitelovPredmet> predmety;

    public MainActivity_adapter(@NonNull Context context, ArrayList<UcitelovPredmet> predmety) {
        super(context, R.layout.activity_main_riadok,predmety);
        this.predmety = predmety;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myInflator = LayoutInflater.from(getContext());
        View customView = myInflator.inflate(R.layout.activity_main_riadok,parent,false);
        final DBhelper databaza = new DBhelper(parent.getContext());

        final UcitelovPredmet predmet =(UcitelovPredmet) getItem(position);

        TextView nazov_predmetu = customView.findViewById(R.id.textView_main_riadok_predmet);
        ImageButton delete_button = customView.findViewById(R.id.imageButton_main_riadok_delete);

        nazov_predmetu.setText(predmet.toString());

        delete_button.setImageResource(R.mipmap.imagebutton_main_delete);

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaza.deleteUcitelovPredmet(predmet);
                predmety.remove(position);
                notifyDataSetInvalidated();
            }
        });



        return customView;
    }
}
