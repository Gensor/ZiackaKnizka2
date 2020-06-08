package com.example.ziackaknizka2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PridajPredmet extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner_predmety;
    Spinner spinner_triedy;
    Predmet predmet;
    Trieda trieda;
    Ucitel ucitel;
    DBhelper databaza ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pridajpredmet);
        Intent intent = getIntent();
        databaza = new DBhelper(this);

        ArrayList<Predmet> predmety = intent.getParcelableArrayListExtra("predmety");
        ArrayList<Trieda> triedy = intent.getParcelableArrayListExtra("triedy");
        ucitel = intent.getParcelableExtra("ucitel");


        spinner_predmety = findViewById(R.id.spinner_predmety);
        ArrayAdapter<Predmet> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, predmety);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_predmety.setAdapter(adapter);
        spinner_predmety.setOnItemSelectedListener(this);

         spinner_triedy = findViewById(R.id.spinner_triedy);
        ArrayAdapter<Trieda> adapter_predmety = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, triedy);
        adapter_predmety.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_triedy.setAdapter(adapter_predmety);
        spinner_triedy.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.spinner_predmety){
            predmet = (Predmet) spinner.getAdapter().getItem(position);
        }
        else{
            trieda = (Trieda) spinner.getAdapter().getItem(position);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        System.out.println("nic nevybrane");
    }

    public void vytvorUciteloviPredmet(View view) {

            databaza.addUcitelovyPredmet(ucitel,predmet,trieda);

            finish();

    }
}
