package com.example.ziackaknizka2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;


public class ZmazPredmet extends AppCompatActivity {

DBhelper databaza;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_zmazpredmet);
        databaza = new DBhelper(this);
        Intent intent = getIntent();

        ArrayList<UcitelovPredmet> predmety = intent.getParcelableArrayListExtra("predmety");

        if (predmety == null) {
            System.out.println("                            predmety su null");
            return;
        }

        ArrayAdapter<UcitelovPredmet> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, predmety);
        final ListView listView = findViewById(R.id.listView_zmazPredmet);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                zmazPredmet(view,(UcitelovPredmet) listView.getItemAtPosition(position));
            }
        });

    }


    public void zmazPredmet(View view, final UcitelovPredmet predmet) {
        DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:

                        databaza.deleteUcitelovPredmet(predmet);
                        finish();

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Naozaj chces zmazat\n"+predmet+" ?").setPositiveButton("ano", clickListener)
                .setNegativeButton("nie", clickListener).show();

    }
}
