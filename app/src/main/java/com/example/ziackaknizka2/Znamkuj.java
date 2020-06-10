package com.example.ziackaknizka2;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Znamkuj extends AppCompatActivity {
    TextView meno_priezvisko_textView;
    TextView predmet_textView;
    ListView hodnotenia_listView;
    EditText nazovHodnotenia_editText;
    EditText body_editText;
    ImageButton pridaj_button;
    ArrayList <Hodnotenie> hodnotenia = new ArrayList<>();
    DBhelper databaza ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hodnotenie);
        databaza = new DBhelper(this);

        Intent intent = getIntent();
        final Ziak ziak = intent.getParcelableExtra("ziak");
        final UcitelovPredmet ucitelovPredmet = intent.getParcelableExtra("predmet");

        hodnotenia_listView = findViewById(R.id.listview_hodnotenie);
        ukazHodnotenia(ucitelovPredmet,ziak);

        predmet_textView = findViewById(R.id.textView_hodnotenie_predmet);
        predmet_textView.setText(ucitelovPredmet.getPredmet().toString());
        meno_priezvisko_textView = findViewById(R.id.textView_hodnotenie_menopriezvisko);
        meno_priezvisko_textView.setText(ziak.toString());

        nazovHodnotenia_editText = findViewById(R.id.editText_Znamkuj_nazov);
        body_editText = findViewById(R.id.editText_Znamkuj_body);
        pridaj_button = findViewById(R.id.imageButton_Znamkuj_pridaj);
        pridaj_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nazovHodnotenia = nazovHodnotenia_editText.getText().toString();
                int body =Integer.parseInt( body_editText.getText().toString());
                Hodnotenie hodnotenie = new Hodnotenie(0,nazovHodnotenia,body,ucitelovPredmet,ziak);
                databaza.addHodnotenie(hodnotenie);
                ukazHodnotenia(ucitelovPredmet,ziak);
            }
        });


    }

    private void ukazHodnotenia(UcitelovPredmet predmet, Ziak ziak){
            hodnotenia = databaza.getVsetkyHodnotenia(predmet,ziak);
            Znamkuj_Adapter adapter = new Znamkuj_Adapter(Znamkuj.this, hodnotenia);
            hodnotenia_listView.setAdapter(adapter);

    }



}
