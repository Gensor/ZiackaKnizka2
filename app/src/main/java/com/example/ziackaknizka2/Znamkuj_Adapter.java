package com.example.ziackaknizka2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Znamkuj_Adapter extends ArrayAdapter {


    public Znamkuj_Adapter(@NonNull Context context, ArrayList<Hodnotenie> hodnotenia) {
        super(context,R.layout.activity_hodnotenie_riadok, hodnotenia);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater myInflator = LayoutInflater.from(getContext());
        View customView = myInflator.inflate(R.layout.activity_hodnotenie_riadok,parent,false);

        Hodnotenie hodnotenie =(Hodnotenie) getItem(position);

        TextView nazov = customView.findViewById(R.id.textView_hodnotenieRiadok_nazovHodnotenia);
        TextView body = customView.findViewById(R.id.textView_hodnotenieRiadok_bodyHodnotenia);
        ImageButton uprav = customView.findViewById(R.id.imageButton_hodnotenieRiadok_uprav);
        ImageButton zmaz = customView.findViewById(R.id.imageButton_hodnotenieRiadok_zmaz);

        nazov.setText(hodnotenie.getNazov());
        body.setText(""+hodnotenie.getBody());
        uprav.setImageResource(R.drawable.add);
        zmaz.setImageResource(R.drawable.delete);

        return customView;
    }
}
