package com.example.ziackaknizka2;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private DBhelper databaza ;
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
        //TODO: zmena ucitela po kliknuti na jeho meno
        ucitel=databaza.getUcitel(1);
        ukazPredmetyvListe(ucitel);
    }

    public void zmazPredmet(View view) {
        ArrayList<UcitelovPredmet> predmety = databaza.getVsetkyUcitelovePredmety(ucitel);
        Intent intent = new Intent(this,ZmazPredmet.class);
        intent.putExtra("predmety",predmety);
        startActivity(intent);

    }
}
