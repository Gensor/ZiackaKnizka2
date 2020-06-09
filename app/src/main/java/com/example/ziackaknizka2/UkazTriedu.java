package com.example.ziackaknizka2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class UkazTriedu extends AppCompatActivity {
    ListView listView;
    TextView textView_predmet;
    TextView textView_trieda ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ukaztriedu);
        listView = findViewById(R.id.listView_ukazZiakov);
        textView_predmet = findViewById(R.id.textView_ukaztriedu_predmet);
        textView_trieda = findViewById(R.id.textView_ukaztriedu_trieda);

        final Intent intent = getIntent();
        DBhelper databaza = new DBhelper(this);
        final UcitelovPredmet predmet = intent.getParcelableExtra("predmet");

        textView_predmet.setText("Predmet: "+predmet.getPredmet().toString());
        textView_trieda.setText("Trieda: "+predmet.getTrieda());

        final ArrayList<Ziak> ziaci = databaza.getZiakov(predmet.getTrieda());
        ArrayAdapter<Ziak> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,ziaci);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent2 = new Intent(UkazTriedu.this,Hodnotenie.class);
                intent2.putExtra("predmet",predmet);
                System.out.println(ziaci.get(position).getTrieda());
                intent2.putExtra("ziak",ziaci.get(position));
                startActivity(intent2);
            }
        });

    }
}
