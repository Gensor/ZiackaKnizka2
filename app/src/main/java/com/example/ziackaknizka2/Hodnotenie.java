package com.example.ziackaknizka2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Hodnotenie extends AppCompatActivity {
    TextView meno_priezvisko;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hodnotenie);

        Intent intent = getIntent();
        Ziak ziak = intent.getParcelableExtra("ziak");
        meno_priezvisko = findViewById(R.id.textView_hodnotenie_menopriezvisko);
        meno_priezvisko.setText(ziak.toString());

    }
}
