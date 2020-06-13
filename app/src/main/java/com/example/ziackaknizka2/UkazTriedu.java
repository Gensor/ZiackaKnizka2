package com.example.ziackaknizka2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class UkazTriedu extends AppCompatActivity {
    private ListView listView;
    private DBhelper databaza;
    private UcitelovPredmet predmet;
    private ArrayList<Ziak> ziaci;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ukaztriedu);
        listView = findViewById(R.id.listview_ukaztriedu);
        TextView textView_predmet = findViewById(R.id.textView_ukaztriedu_predmet);
        TextView textView_trieda = findViewById(R.id.textView_ukaztriedu_trieda);

        final Intent intent = getIntent();
        databaza = new DBhelper(this);
        predmet = intent.getParcelableExtra("predmet");

        textView_predmet.setText(predmet.getPredmet().toString());
        textView_trieda.setText("Trieda: "+predmet.getTrieda());

        ziaci = databaza.getZiakov(predmet.getTrieda());
        UkazTriedu_adapter adapter = new UkazTriedu_adapter(this,ziaci,predmet);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent_znamkuj = new Intent(UkazTriedu.this, Znamkuj.class);
                intent_znamkuj.putExtra("predmet",predmet);
                intent_znamkuj.putExtra("ziak",ziaci.get(position));
                startActivity(intent_znamkuj);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ziaci = databaza.getZiakov(predmet.getTrieda());
        UkazTriedu_adapter adapter = new UkazTriedu_adapter(this,ziaci,predmet);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        databaza.close();
        super.onDestroy();
    }
}
