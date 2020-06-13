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
    private ArrayList<Hodnotenie> hodnotenia;

    public Znamkuj_Adapter(@NonNull Context context, ArrayList<Hodnotenie> hodnotenia) {
        super(context,R.layout.activity_znamkuj_riadok, hodnotenia);
        this.hodnotenia = hodnotenia;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {

        LayoutInflater myInflator = LayoutInflater.from(getContext());
        View customView = myInflator.inflate(R.layout.activity_znamkuj_riadok,parent,false);
        final DBhelper databaza = new DBhelper(parent.getContext());
        final Hodnotenie hodnotenie =(Hodnotenie) getItem(position);

        TextView textview_nazov = customView.findViewById(R.id.textView_znamkuj_riadok_nazovHodnotenia);
        TextView textview_body = customView.findViewById(R.id.textView_znamkuj_riadok_bodyHodnotenia);
        ImageButton button_uprav = customView.findViewById(R.id.imageButton_znamkuj_riadok_uprav);
        ImageButton button_zmaz = customView.findViewById(R.id.imageButton_znamkuj_riadok_zmaz);

        assert hodnotenie != null;
        textview_nazov.setText(hodnotenie.getNazov());
        textview_body.setText(""+hodnotenie.getBody());

        button_zmaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaza.deleteHodnotenie((Hodnotenie)getItem(position));
                hodnotenia.remove(position);
                notifyDataSetInvalidated();
            }
        });

        button_uprav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(),UpravHodnotenie.class);
                intent.putExtra("hodnotenie", hodnotenie);
                parent.getContext().startActivity(intent);
            }
        });
        return customView;
    }
}
