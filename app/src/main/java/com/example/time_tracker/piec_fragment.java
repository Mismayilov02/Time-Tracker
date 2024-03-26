package com.example.time_tracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class piec_fragment extends Fragment {

Cursor cursor;
Dialog dialog;
    piece_adapter piece_adapter;
boolean add = false , all= true  , bool_startdate = false , bool_enddate = false;
    SimpleDateFormat simple_timeformat;
    Button start_date_time , end_date_time , all_date_time;
    SQLiteDatabase database;
    boolean scrool_true = true;

    Calendar calendar , calendar_end;
    Date time_start_convert , time_start , time_end;

    String start_date , end_date;
    DatePickerDialog.OnDateSetListener dateSetListener , dateSetListener_start;

    ArrayList<Long> ay_list = new ArrayList<>();
    ArrayList<Long> hefte_list = new ArrayList<>();
    ListView listView ;

    int gun =1 , ay = 1  , il = 2022 , a = 0 , b=0;
    int[] aylar = new int[]{31,28,31,30,31,30,31,31,30,31,30,31};
    int start_year_s , end_year_s , start_mont_s  ,end_mont_s , start_day_s , end_day_s ;
    int name_index , kataqori_index , total_index , time_start_index , icon_index;

    ArrayList<Date> date_array_week = new ArrayList<>();
    ArrayList<Date> data_array_day = new ArrayList<>();
    ArrayList<Date> data_array_sellect = new ArrayList<>();

    ArrayList<ArrayList> name_array = new ArrayList<>();
    ArrayList<ArrayList> icon_array = new ArrayList<>();
    ArrayList<ArrayList> total_array = new ArrayList<>();
    ArrayList<String>time_araliq = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_piec_fragment, container, false);

        start_date_time = v.findViewById(R.id.start_date_time);
        end_date_time = v.findViewById(R.id.end_date_time);
        all_date_time = v.findViewById(R.id.all_date_time);
        listView = v.findViewById(R.id.piece_list);


        database = getActivity().openOrCreateDatabase("Oracle" , Context.MODE_PRIVATE,null);
        simple_timeformat = new SimpleDateFormat("dd-MM-yyyy");


        get_data_day();
        get_data_all();
        get_adapter();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                adapter_piece_adapter adapter_piece_adapter = new adapter_piece_adapter(piece_adapter.context ,piece_adapter.name_array.get(i) ,piece_adapter.icon_array.get(i) ,piece_adapter.total_array.get(i));
              get_structur_artel(adapter_piece_adapter);

            }
        });


        start_date_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
               int start_year = calendar.get(Calendar.YEAR);
               int start_mont = calendar.get(Calendar.MONTH);
               int start_day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext() , android.R.style.Theme_DeviceDefault_Dialog, dateSetListener_start , start_year,start_mont,start_day);
                datePickerDialog.show();


            }
        });
        dateSetListener_start = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                start_day_s = day;
                start_mont_s = month;
                start_year_s = year;
                start_date =day + "-" + month + "-" + year;
                System.out.println("start");

                bool_startdate = true;
                if(bool_enddate && bool_startdate){
                    all = false;
                    get_data_sellect();
                }

                try {
                    time_start = simple_timeformat.parse(start_date);
                }catch (Exception e){

                }
            }
        };


        end_date_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar_end = Calendar.getInstance();
               int end_year = calendar.get(Calendar.YEAR);
              int  end_mont = calendar.get(Calendar.MONTH);
                int end_day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext() , android.R.style.Theme_DeviceDefault_Dialog, dateSetListener , end_year,end_mont,end_day);
                datePickerDialog.show();


            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

               end_day_s = day;
               end_mont_s = month;
               end_year_s = year;
                end_date =day + "-" + month + "-" + year;
                bool_enddate = true;
                System.out.println("end");
                if(bool_enddate && bool_startdate){
                    all = false;
                    get_data_sellect();
                }

                try {
                    time_end = simple_timeformat.parse(end_date);
                }catch (Exception e){

                }
                System.out.println(time_end.getTime());
             //   System.out.println(Math.abs(time_end.getTime() - time_start_convert.getTime()));
            }
        };

        all_date_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               all = true;
               bool_enddate  =false;
               bool_startdate = false;

               get_data_day();
            }
        });

       // get_adapter();

        return  v;
    }

    public  void get_data_all(){

        for(int i =0 ; i<data_array_day.size()-1 ; i++) {
          //  System.out.println(i);
            ArrayList<String> name = new ArrayList<>();
            ArrayList<Bitmap> icon = new ArrayList<>();
            ArrayList<Long> total = new ArrayList<>();
          //  ArrayList<String> name = new ArrayList<>();


            cursor = database.rawQuery("SELECT *  FROM timer_project WHERE time_start > ? AND time_start < ?", new String[]{String.valueOf(data_array_day.get(i).getTime()), String.valueOf(data_array_day.get(i + 1).getTime())}, null);
            name_index = cursor.getColumnIndex("name");
            icon_index = cursor.getColumnIndex("icon");
          //  kataqori_index = cursor.getColumnIndex("kataqori");
            total_index = cursor.getColumnIndex("total");
           // time_start_index = cursor.getColumnIndex("time_start");


            while (cursor.moveToNext()) {
                   add =true;


                byte[] icon_byte = cursor.getBlob(icon_index);
                Bitmap icon_bitmap = BitmapFactory.decodeByteArray(icon_byte, 0, icon_byte.length);
                icon.add(icon_bitmap);
                name.add(cursor.getString(name_index));
                total.add(cursor.getLong(total_index));

            }
            cursor.close();

            if(add) {
                name_array.add(name);
                icon_array.add(icon);
                total_array.add(total);
                time_araliq.add(simple_timeformat.format(data_array_day.get(i))+" / "+simple_timeformat.format(data_array_day.get(i+1)));
                add =false;
            }
        }
    }

    public  void  get_data_day(){

        data_array_day.clear();
        for(int i = 2 ; i<4 ;i++){
            for(int j = 1; j<13 ; j++){
                for(int k = 1 ; k<(aylar[b]+1) ; k++){
                    String str_date = k +"-"+j+"-202"+i;
                    //System.out.println(str_date);
                    try {
                        Date date = simple_timeformat.parse(str_date);
                        data_array_day.add(date);
                      //  System.out.println(date);
                    }catch (Exception e){

                    }
                }
                b++;

            }
            b = 0;
        }
    }

    public  void get_data_week(){

        date_array_week.clear();
        for(int i =0 ; i<600 ; i+=7  ){
            String str_date = gun +"-"+ay+"-"+il;
            try {
                Date date = simple_timeformat.parse(str_date);
                date_array_week.add(date);
            }catch (Exception e){

            }
            if((gun+7) <aylar[a]){
                gun+=7;

            }else if((gun+7) >aylar[a]){
                gun = (gun+7)-aylar[a];
                a++;
                if(a>12){
                    a=0;
                }

                if((ay+1) <13){
                    ay++;
                }else if((ay+1)>12){
                    ay = 1;
                    il++;
                }
            }
        }
    }

    public  void  get_adapter(){
         piece_adapter  = new piece_adapter(getActivity() ,name_array ,icon_array , total_array,time_araliq);
        listView.setAdapter(piece_adapter);
    }

    public  void get_data_sellect(){
        int all;
        int year_sellec = Math.abs(end_year_s-start_year_s)*365;
        int month_sellect=0;
        int day_sellec;
        if(start_mont_s !=end_mont_s) {
            for (int i = start_mont_s + 1; i < end_mont_s; i++) {
                month_sellect += aylar[i];
            }
        }
        day_sellec = (aylar[start_mont_s-1] - start_day_s) +end_day_s;
        System.out.println("day -" +day_sellec);

        all = year_sellec +month_sellect+day_sellec;
        System.out.println(all);

        int ay = start_mont_s;
       int  array_ay= start_mont_s-1;
        int  il = start_year_s;
        int gun = start_day_s;

        for(int i = start_day_s ; i<all ; i++){

            String str_date = gun +"-"+ay+"-202"+il;
            System.out.println(start_date);
            try {
                Date date = simple_timeformat.parse(str_date);
                data_array_sellect.add(date);
            }catch (Exception e){

            }
            if((gun+1) <aylar[array_ay]){
                gun+=1;

            }else if((gun+1) >aylar[array_ay]){
                gun = (gun+1)-aylar[array_ay];
                array_ay++;
                if(array_ay>12){
                    array_ay=0;
                }

                if((ay+1) <13){
                    ay++;
                }else if((ay+1)>12){
                    ay = 1;
                    il++;
                }
            }
        }
    }

    public void get_structur_artel(adapter_piece_adapter adapter ){
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.structor_artel);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
        Button dialog_btn = dialog.findViewById(R.id.structor_exit_btn);
        ListView listView = dialog.findViewById(R.id.structor_listview);
        dialog_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        listView.setAdapter(adapter);

    }
}