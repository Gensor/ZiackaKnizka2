package com.example.ziackaknizka2;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private DBhelper databaza ;
    private Ucitel ucitel;
    ListView listView_zoznamPredmetov;
    Button button_pridajPredmet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaza = new DBhelper(this);

        ucitel = databaza.getUcitel(1);

        TextView menoUcitela = findViewById(R.id.textView_main_meno);
        button_pridajPredmet = findViewById(R.id.button_main_pridaj);
        button_pridajPredmet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pridajPredmet(v);
            }
        });

        menoUcitela.setText(ucitel.toString());

        ukazPredmetyvListe(ucitel);
    }

    public void ukazPredmetyvListe(Ucitel ucitel){
        final ArrayList<UcitelovPredmet> predmety = databaza.getVsetkyUcitelovePredmety(ucitel);
        ArrayAdapter<UcitelovPredmet> adapter = new MainActivity_adapter(MainActivity.this,predmety);
        listView_zoznamPredmetov = findViewById(R.id.listview_main_predmety);
        listView_zoznamPredmetov.setAdapter(adapter);
        listView_zoznamPredmetov.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, UkazTriedu.class );
                intent.putExtra("predmet",predmety.get(position));
                startActivity(intent);
            }
        });
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
        ucitel=databaza.getUcitel(1);
        ukazPredmetyvListe(ucitel);
    }

    @Override
    protected void onDestroy() {
        databaza.close();
        super.onDestroy();
    }
}
