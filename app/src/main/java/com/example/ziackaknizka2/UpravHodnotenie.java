package com.example.ziackaknizka2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UpravHodnotenie extends AppCompatActivity {
    private EditText editText_nazov;
    private EditText editText_body;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hodnotenie_riadok_uprav);

        Intent intent = getIntent();
        final Hodnotenie hodnotenie = intent.getParcelableExtra("hodnotenie");
        final DBhelper databaza = new DBhelper(this);
        editText_nazov = findViewById(R.id.editText_upravhodnotenie_nazov);
        editText_body = findViewById(R.id.editText_upravhodnotenie_body);
        ImageButton imageButton_ok = findViewById(R.id.imageButton_upravhodnotenie_uprav);

        editText_nazov.setText(hodnotenie.getNazov(), TextView.BufferType.EDITABLE);
        editText_body.setText("" + hodnotenie.getBody(), TextView.BufferType.EDITABLE);

        imageButton_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // if( (nazovHodnotenia.isEmpty()) || (body < 0) )return;
                hodnotenie.setNazov(editText_nazov.getText().toString());
                hodnotenie.setBody(Integer.parseInt(editText_body.getText().toString()));
                System.out.println(hodnotenie.toString());
                databaza.updateHodnotenie(hodnotenie);
                schovajKlavesnicu(v);
                finish();

            }
        });

    }


   void schovajKlavesnicu(View v) {

        if( v !=null){
            InputMethodManager imm = (InputMethodManager) getSystemService(getBaseContext().INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
}

}
