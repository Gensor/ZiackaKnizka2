package com.example.ziackaknizka2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class Znamkuj extends AppCompatActivity {
    private TextView body_textView;
    private ListView hodnotenia_listView;
    private EditText nazovHodnotenia_editText;
    private EditText body_editText;
    private ArrayList <Hodnotenie> hodnotenia = new ArrayList<>();
    private DBhelper databaza ;
    private UcitelovPredmet predmet;
    private Ziak ziak;
    private Spinner znamka_spinner;
    private Znamka znamka;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_znamkuj);
        String[] znamky = new String[]{"A", "B", "C", "D", "E", "Fx", ""};
        databaza = new DBhelper(this);

        Intent intent = getIntent();
        ziak = intent.getParcelableExtra("ziak");
        predmet = intent.getParcelableExtra("predmet");

        hodnotenia_listView = findViewById(R.id.listview_znamkuj_list);
        znamka_spinner = findViewById(R.id.spinner_znamkuj_znamka);

        TextView predmet_textView = findViewById(R.id.textview_znamkuj_predmet);
        predmet_textView.setText(predmet.getPredmet().toString());

        TextView meno_priezvisko_textView = findViewById(R.id.textView_znamkuj_meno);
        meno_priezvisko_textView.setText(ziak.toString());

        body_textView = findViewById(R.id.textView_znamkuj_body);
        nazovHodnotenia_editText = findViewById(R.id.editText_znamkuj_hodnotenienazov);
        body_editText = findViewById(R.id.editText_znamkuj_bodovanie);
        ImageButton pridaj_button = findViewById(R.id.imageButton_znamkuj_pridaj);

        ArrayAdapter<String> adapterZnamky = new ArrayAdapter<>(this,R.layout.spinner_znamka, znamky);
        adapterZnamky.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        znamka_spinner.setAdapter(adapterZnamky);
        ukazHodnotenia();
        znamka_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String zvolenaZnamka = znamka_spinner.getAdapter().getItem(position).toString();
                znamka.setZnamka(zvolenaZnamka);
                databaza.updateZnamka(znamka);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        pridaj_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nazovHodnotenia = nazovHodnotenia_editText.getText().toString();

                int body = Integer.parseInt( body_editText.getText().toString() );

                if( (nazovHodnotenia.isEmpty()) || (body < 0) )return;

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
            Znamkuj_Adapter adapter = new Znamkuj_Adapter(this, hodnotenia);
            hodnotenia_listView.setAdapter(adapter);
            ukazZnamku();
    }

    private void ukazZnamku(){
        int body = 0;
        for(Hodnotenie hodnotenie:hodnotenia){
            body+=hodnotenie.getBody();
        }

        if(databaza.hasZnamka(predmet,ziak)){
            znamka = databaza.getZnamka(ziak,predmet);
            String i = znamka.getZnamka();

            switch (i){
                case "A": znamka_spinner.setSelection(0);
                        break;
                case "B": znamka_spinner.setSelection(1);
                    break;
                case "C": znamka_spinner.setSelection(2);
                    break;
                case "D": znamka_spinner.setSelection(3);
                    break;
                case "E": znamka_spinner.setSelection(4);
                    break;
                case "Fx": znamka_spinner.setSelection(5);
                    break;
                default: znamka_spinner.setSelection(6);
            }
        }else{
            znamka = new Znamka(0,ziak,predmet,"");
            databaza.addZnamka(znamka);
            znamka_spinner.setSelection(6);
        }
        body_textView.setText("body: "+body);
    }

    @Override
    protected void onDestroy() {
        databaza.close();
        super.onDestroy();
    }
}
