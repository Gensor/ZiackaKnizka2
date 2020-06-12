package com.example.ziackaknizka2;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
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
    TextView body_textView;
    ListView hodnotenia_listView;
    EditText nazovHodnotenia_editText;
    EditText body_editText;
    ImageButton pridaj_button;
    ArrayList <Hodnotenie> hodnotenia = new ArrayList<>();
    DBhelper databaza ;
    UcitelovPredmet predmet;
    Ziak ziak;
    Znamkuj_Adapter adapter;
    int body =0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hodnotenie);
        databaza = new DBhelper(this);

        Intent intent = getIntent();
         ziak = intent.getParcelableExtra("ziak");
         predmet = intent.getParcelableExtra("predmet");


        hodnotenia_listView = findViewById(R.id.listview_znamkuj_list);

        predmet_textView = findViewById(R.id.textview_znamkuj_predmet);
        predmet_textView.setText(predmet.getPredmet().toString());
        meno_priezvisko_textView = findViewById(R.id.textView_znamkuj_meno);
        body_textView = findViewById(R.id.textView_znamkuj_body);
        meno_priezvisko_textView.setText(ziak.toString());

        nazovHodnotenia_editText = findViewById(R.id.editText_znamkuj_hodnotenienazov);
        body_editText = findViewById(R.id.editText_znamkuj_bodovanie);
        pridaj_button = findViewById(R.id.imageButton_znamkuj_pridaj);

        ukazHodnotenia();

        pridaj_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nazovHodnotenia = nazovHodnotenia_editText.getText().toString();
                int body =Integer.parseInt( body_editText.getText().toString());
                Hodnotenie hodnotenie = new Hodnotenie(0,nazovHodnotenia,body,predmet,ziak);

                databaza.addHodnotenie(hodnotenie);
                ukazHodnotenia();


            }
        });






    }

    @Override
    protected void onResume() {
        super.onResume();
        ukazHodnotenia();

    }


    private void ukazHodnotenia(){
            hodnotenia = databaza.getVsetkyHodnotenia(predmet,ziak);
            adapter = new Znamkuj_Adapter(this,hodnotenia);
            hodnotenia_listView.setAdapter(adapter);
            ukazZnamku();


    }

    private void ukazZnamku(){
        body = 0;
        for(Hodnotenie hodnotenie:hodnotenia){
            body+=hodnotenie.getBody();
        }
      //  body_textView.setText(""+body);
        /*String result="";
        if(body>=93){
            result = "A";
        }else if(body>=85){
            result = "B";
        }else if(body>=77){
            result = "C";
        }else if(body>=69){
            result = "D";
        }else if(body>=60){
            result = "E";
        }else{
            result = "FX";
        }*/

        body_textView.setText("body:"+body);
    }

}
