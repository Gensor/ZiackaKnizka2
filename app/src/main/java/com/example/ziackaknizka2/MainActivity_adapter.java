package com.example.ziackaknizka2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
                /*databaza.deleteUcitelovPredmet(predmet);
                predmety.remove(position);
                notifyDataSetInvalidated();*/
                zmazPredmet(v,predmet,databaza,position);
            }
        });



        return customView;
    }


    public void zmazPredmet(View view, final UcitelovPredmet predmet, final DBhelper databaza, final int position) {
        DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:

                        databaza.deleteUcitelovPredmet(predmet);
                        predmety.remove(position);
                        notifyDataSetInvalidated();


                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Naozaj si chces zmazat\n"+predmet+" ?").setPositiveButton("ano", clickListener)
                .setNegativeButton("nie", clickListener).show();

    }


}
