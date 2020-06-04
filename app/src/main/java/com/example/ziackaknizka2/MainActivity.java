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
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    DBhelper databaza ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaza = new DBhelper(this);

        //test
           Osoba[] ludia =databaza.ukazMiLudi();
           ArrayList<Osoba> ludia2 =new ArrayList<>();
        for (Osoba os:ludia) {
            if(os!=null)ludia2.add(os);
        }
        for (Osoba os:ludia2) {
            System.out.println(os);
        }
        //testkoniec
            ArrayAdapter<Osoba> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,ludia2);//test
            ListView listView = findViewById(R.id.listView_uvod_predmety);
            listView.setAdapter(adapter);

    }


}
