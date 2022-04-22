package com.example.time_tracker;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airbnb.lottie.animation.content.Content;

import java.util.ArrayList;

public class piece_adapter extends ArrayAdapter<String> {

    ArrayList<ArrayList> name_array = new ArrayList<>();
    ArrayList<ArrayList> icon_array = new ArrayList<>();
    ArrayList<ArrayList> total_array = new ArrayList<>();
    ArrayList<String>time_araliq = new ArrayList<>();
    Context context;

    public   piece_adapter(Context context , ArrayList<ArrayList>name_array , ArrayList<ArrayList> icon_array , ArrayList<ArrayList> total_array , ArrayList<String> time_araliq){
        super( context, R.layout.data_design ,R.id.data_text, time_araliq);

        this.icon_array = icon_array;
        this.name_array = name_array;
        this.time_araliq = time_araliq;
        this.total_array = total_array;
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v =  LayoutInflater.from(context).inflate(R.layout.data_design , null);

        ListView data_design_list = v.findViewById(R.id.all_listview_v);
        TextView data_text = v.findViewById(R.id.data_text);

       adapter_piece_adapter adapter_piece_adapter = new adapter_piece_adapter(context ,name_array.get(position) ,icon_array.get(position) ,total_array.get(position));
        data_design_list.setAdapter(adapter_piece_adapter);
        data_text.setText(time_araliq.get(position));


        return v;
    }
}

