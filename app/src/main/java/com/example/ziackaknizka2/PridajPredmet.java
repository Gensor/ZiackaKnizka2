package com.example.ziackaknizka2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class PridajPredmet extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Predmet predmet;
    private Trieda trieda;
    private Ucitel ucitel;
    private DBhelper databaza ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pridajpredmet);
        databaza = new DBhelper(this);

        Intent intent = getIntent();
            ArrayList<Predmet> predmety = intent.getParcelableArrayListExtra("predmety");
            ArrayList<Trieda> triedy = intent.getParcelableArrayListExtra("triedy");
            ucitel = intent.getParcelableExtra("ucitel");

        ImageButton button_pridaj = findViewById(R.id.imagebutton_pridajpredmet_ok);

        Spinner spinner_predmety = findViewById(R.id.spinner_pridajpredmet_predmety);
            ArrayAdapter<Predmet> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, predmety);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_predmety.setAdapter(adapter);
            spinner_predmety.setOnItemSelectedListener(this);

        Spinner spinner_triedy = findViewById(R.id.spinner_pridajpredmet_triedy);
            ArrayAdapter<Trieda> adapter_predmety = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, triedy);
            adapter_predmety.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_triedy.setAdapter(adapter_predmety);
            spinner_triedy.setOnItemSelectedListener(this);

        button_pridaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaza.addUcitelovyPredmet(ucitel,predmet,trieda);
                databaza.close();
                finish();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.spinner_pridajpredmet_predmety){
            predmet = (Predmet) spinner.getAdapter().getItem(position);
        }
        else{
            trieda = (Trieda) spinner.getAdapter().getItem(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}
