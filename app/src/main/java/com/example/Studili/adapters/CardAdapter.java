package com.example.Studili.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.Studili.R;
import com.example.Studili.classes.Stat_Card;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends ArrayAdapter<Stat_Card> {
    private Context ct;
    private ArrayList<Stat_Card> arr;

    public CardAdapter(@NonNull Context context, int resource, @NonNull List<Stat_Card> objects) {
        super(context, resource, objects);
        this.ct = context;
        this.arr = new ArrayList<>(objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
        {
            LayoutInflater i = (LayoutInflater)ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = i.inflate(R.layout.stat_card, null);
        }
        if (arr.size() > 0){
            Stat_Card card = arr.get(position);
            ImageView imgStat = convertView.findViewById(R.id.stat_image);
            TextView title = convertView.findViewById(R.id.stat_title);
            TextView desc = convertView.findViewById(R.id.stat_desc);


            imgStat.setImageResource(card.image);
            title.setText(card.title);
            desc.setText(card.desc);

        }
        return convertView;
    }
}