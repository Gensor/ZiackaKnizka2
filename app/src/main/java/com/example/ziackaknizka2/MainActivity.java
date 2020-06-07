package com.example.ziackaknizka2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    static final int ZAPNI_PRIDAJPREDMET_ACTIVITY = 1;
    DBhelper databaza ;
    private Ucitel ucitel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaza = new DBhelper(this);

        ucitel = databaza.getUcitel(1);

        TextView menoUcitela = findViewById(R.id.textView_mainActivity_menoUcitela);
        menoUcitela.setText(ucitel.toString());

        ukazPredmetyvListe(ucitel);

    }

    public void ukazPredmetyvListe(Ucitel ucitel){
        ArrayList<UcitelovPredmet> predmety = databaza.getVsetkyUcitelovePredmety(ucitel);
        ArrayAdapter<UcitelovPredmet> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,predmety);
        ListView listView = findViewById(R.id.listView_uvod_predmety);
        listView.setAdapter(adapter);
    }


    public void pridajPredmet(View view){
        Intent intent = new Intent(this, PridajPredmet.class );
        ArrayList<Predmet> predmety = databaza.getVsetkyPredmety();
        ArrayList<Trieda> triedy = databaza.getVsetkyTriedy();
        intent.putExtra("predmety", predmety );
        intent.putExtra("triedy", triedy);
        intent.putExtra("ucitel",ucitel);
        startActivity(intent);


    }

    @Override
    protected void onResume() {
        super.onResume();
        ukazPredmetyvListe(ucitel);
    }
}
