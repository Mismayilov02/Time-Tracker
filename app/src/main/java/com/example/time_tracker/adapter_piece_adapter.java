package com.example.time_tracker;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class adapter_piece_adapter extends ArrayAdapter<String> {

    ArrayList<String> name = new ArrayList<>();
    ArrayList<Bitmap> icon = new ArrayList<>();
    ArrayList<Long> total = new ArrayList<>();
    Context context;

    public adapter_piece_adapter(Context context, ArrayList<String> name, ArrayList<Bitmap> icon, ArrayList<Long> total) {
        super(context, R.layout.structor_design,R.id.structor_time_text, name);
        this.icon = icon;
        this.total = total;
        this.name = name;
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v =  LayoutInflater.from(context).inflate(R.layout.structor_design , null);
        TextView structor_name_text  = v.findViewById(R.id.structor_name_text);
        ImageView structor_icon = v.findViewById(R.id.structor_icon);
        TextView structor_time_text = v.findViewById(R.id.structor_time_text);

        structor_name_text.setText(name.get(position));
        structor_icon.setImageBitmap(icon.get(position));
        long saat = TimeUnit.MILLISECONDS.toHours(total.get(position))%24;
        long dakika = TimeUnit.MILLISECONDS.toMinutes(total.get(position))%60;
        long saniye = TimeUnit.MILLISECONDS.toSeconds(total.get(position))%60;
        structor_time_text.setText(saat+":"+dakika+":"+saniye);



        return v;
    }
}
