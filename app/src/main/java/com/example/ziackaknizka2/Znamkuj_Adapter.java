package com.example.ziackaknizka2;

import android.content.Context;
import android.content.Intent;
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
private Context context;
ArrayList<Hodnotenie> hodnotenia;
    public Znamkuj_Adapter(@NonNull Context context, ArrayList<Hodnotenie> hodnotenia) {
        super(context,R.layout.activity_hodnotenie_riadok, hodnotenia);
        this.context = context;
        this.hodnotenia = hodnotenia;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {

        LayoutInflater myInflator = LayoutInflater.from(getContext());
        View customView = myInflator.inflate(R.layout.activity_hodnotenie_riadok,parent,false);
        final DBhelper databaza = new DBhelper(parent.getContext());

        final Hodnotenie hodnotenie =(Hodnotenie) getItem(position);

        TextView nazov = customView.findViewById(R.id.textView_hodnotenieRiadok_nazovHodnotenia);
        TextView body = customView.findViewById(R.id.textView_hodnotenieRiadok_bodyHodnotenia);
        ImageButton uprav = customView.findViewById(R.id.imageButton_hodnotenieRiadok_uprav);
        ImageButton zmaz = customView.findViewById(R.id.imageButton_hodnotenieRiadok_zmaz);

        nazov.setText(hodnotenie.getNazov());
        body.setText(""+hodnotenie.getBody());
        uprav.setImageResource(R.mipmap.imagebutton_znamkujadapter_uprav);
        zmaz.setImageResource(R.mipmap.imagebutton_znamkujadapter_zmaz);

        zmaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaza.deleteHodnotenie((Hodnotenie)getItem(position));
                hodnotenia.remove(position);
                notifyDataSetInvalidated();
            }
        });

        uprav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,UpravHodnotenie.class);
                intent.putExtra("hodnotenie", hodnotenie);
                context.startActivity(intent);
            }
        });

        return customView;
    }








}
