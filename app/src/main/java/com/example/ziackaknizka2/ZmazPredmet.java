package com.example.ziackaknizka2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ZmazPredmet extends AppCompatActivity {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_zmazpredmet);

        Intent intent = getIntent();

        ArrayList<UcitelovPredmet> predmety = intent.getParcelableArrayListExtra("predmety");

        if(predmety==null){
            System.out.println("                            predmety su null");
            return;
        }
int i = 0;
        for(UcitelovPredmet up:predmety){
            System.out.println(i+".");
            i++;
            System.out.println(up);
        }
        ArrayAdapter<UcitelovPredmet> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,predmety);
        ListView listView = findViewById(R.id.listView_zmazPredmet);
        listView.setAdapter(adapter);

    }





}
