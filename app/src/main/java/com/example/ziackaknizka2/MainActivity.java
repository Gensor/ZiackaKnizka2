package com.example.ziackaknizka2;

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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Predmet predmet1 = new Predmet("nejaky predmet jedna");
        Predmet predmet2 = new Predmet("uplne iny predmet dva");
        Ucitel ucitel = new Ucitel("Jano","Nejaky");
        UcitelovPredmet ucitelovPredmet1 = new UcitelovPredmet(predmet1);
        UcitelovPredmet ucitelovPredmet2 = new UcitelovPredmet(predmet2);
        ucitel.addPredmet(ucitelovPredmet1);
        ucitel.addPredmet(ucitelovPredmet2);
        ArrayList<UcitelovPredmet> predmety = ucitel.getPredmety();
        ArrayAdapter<UcitelovPredmet> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,predmety);
        ListView listView = findViewById(R.id.listView_uvod_predmety);
        listView.setAdapter(adapter);
    }


}
