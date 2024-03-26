package com.example.time_tracker;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class time_fragment extends Fragment {

    SQLiteDatabase database ;
    Runnable  rrunnable;
    SimpleDateFormat simple_timeformat,simple_tamformat;
    Handler hhandler;
    Date ddate ,simple_date ;
    Cursor cursor;
    String date , time_date;
    Calendar calendar;
    Adabter_run adabter_run;
    Dialog dialog;
    Adapter adapter;
    int name_index , text_color_index , bacround_color_index, icon_index , status_index , kataqori_index , time_text_index , time_start_index;
    TextView time_text;
    long eguals ,simple_start;


    LottieAnimationView animationView;

    ArrayList<String> proje_namee = new ArrayList<>();
    ArrayList<Integer> text_color = new ArrayList<>();
    ArrayList<Integer> bacround_color = new ArrayList<>();
    ArrayList<Bitmap> icon = new ArrayList<Bitmap>();
    ArrayList<String> status = new ArrayList<>();
    ArrayList<String> get_time_text = new ArrayList<String>();
    ArrayList<String>katogori = new ArrayList<>();

    ArrayList<String> projename_run = new ArrayList<>();
    ArrayList<Integer> textcolor_run = new ArrayList<>();
    ArrayList<Integer> bacroundcolor_run = new ArrayList<>();
    ArrayList<String>get_time_text_run = new ArrayList<>();
    ArrayList<Bitmap>icon_run = new ArrayList<>();
    ArrayList<String>katogori_run = new ArrayList<>();
    ArrayList<String>time_start = new ArrayList<>();
   ArrayList<Long>total_time = new ArrayList<>();

    String uptade_name ;
    int run_numbers =0;






    ListView all_list_vievw , run_listview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_time_fragment, container, false);



        all_list_vievw = v.findViewById(R.id.all_listview_v);
        run_listview = v.findViewById(R.id.run_listview);
        time_text = v.findViewById(R.id.editTextTime);

        animationView = v.findViewById(R.id.animation_freetime);

        hhandler = new Handler();
        rrunnable  =new Runnable() {
            @Override
            public void run() {
                calendar = Calendar.getInstance();
                ddate = new Date();
              //  start_date = "00-00 00:00:00";
                simple_tamformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

                simple_timeformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

                date = simple_tamformat.format(ddate.getTime());
                time_date = simple_timeformat.format(ddate.getTime());
                time_text.setText(date);



                if(time_start.size()!=0){

                    for(int i=0 ; i<time_start.size(); i++){

                        try {

                            Date time_start_convert = simple_timeformat.parse(time_start.get(i));


                             eguals = Math.abs(ddate.getTime()-time_start_convert.getTime());
                             String lonn = String.valueOf(eguals);
                             total_time.set(i , eguals);


                           // long gun = TimeUnit.MILLISECONDS.toDays(eguals);
                            long saat = TimeUnit.MILLISECONDS.toHours(eguals)%24;
                            long dakika = TimeUnit.MILLISECONDS.toMinutes(eguals)%60;
                            long saniye = TimeUnit.MILLISECONDS.toSeconds(eguals)%60;

                            adabter_run.time_text_run.get(i).setText(saat+":"+dakika+":"+saniye);
                          //  System.out.println("dataa  " + eguals);
                        } catch (Exception e) {
                            System.out.println("eror data new");
                        }

                    }

                }
                hhandler.postDelayed(rrunnable  ,1000);
            }
        };hhandler.post(rrunnable);


        try {
            database = getActivity().openOrCreateDatabase("Oracle" , Context.MODE_PRIVATE,null);
        }catch (Exception e){
            Toast.makeText(getActivity(), String.valueOf(e), Toast.LENGTH_LONG).show();

        }


         get_data();
        run_adapter();
        adapter_all();
        run_listview_animation();

        all_list_vievw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 if(run_numbers <3) {
                     adapter.animation_play.get(i).setVisibility(View.VISIBLE);
                     adapter.animation_play.get(i).playAnimation();
                     adapter.timer_text.get(i).setVisibility(View.INVISIBLE);
                     uptade_name = adapter.project_name.get(i);
                     Handler handler = new Handler();
                     Runnable runnable = new Runnable() {
                         @Override
                         public void run() {
                             adapter.animation_play.get(i).pauseAnimation();
                             adapter.animation_play.get(i).setVisibility(View.INVISIBLE);

                             adapter.timer_text.get(i).setVisibility(View.VISIBLE);
                             try {
                                 database.execSQL("UPDATE new_project SET status = ? , time_start =?  WHERE name =?", new String[]{"true", time_date ,uptade_name});

                               //  System.out.println("uptede success");
                             } catch (Exception e) {
                                 System.out.println(" updte succes erorr");
                             }
                             run_numbers = 0;
                             project_clear();
                             get_data();
                             run_adapter();
                             adapter_all();
                             run_listview_animation();


                         }
                     };
                     handler.postDelayed(runnable, 0);

                 }else {
                     get_runmax_dialog();
                 }

            }
        });



        run_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adabter_run.animation_progres_run.get(i).setVisibility(View.VISIBLE);
                adabter_run.time_text_run.get(i).setVisibility(View.INVISIBLE);
                adabter_run.animation_progres_run.get(i).playAnimation();
                uptade_name = adabter_run.projename_run.get(i);
                database.execSQL("UPDATE new_project SET status = ? , time_start =?  WHERE name =?", new String[]{"false",  "0" , uptade_name});
               // byte[] bytes = conver_bitmap(adapter.iconn.get(i));

                try {
                     simple_date = simple_tamformat.parse(time_start.get(i));
                     simple_start  = simple_date.getTime();
                }catch (Exception e){

                }


               add_new_timeproject(adabter_run.projename_run.get(i) , adabter_run.catogori_text.get(i) ,total_time.get(i) , simple_start  ,icon_run.get(i) );


                Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        adabter_run.animation_progres_run.get(i).pauseAnimation();
                        run_numbers =0;
                        project_clear();
                        get_data();
                        run_adapter();
                        adapter_all();
                        run_listview_animation();
                    }
                };handler.postDelayed(runnable , 0);
            }
        });

        return  v;
    }




    public  void  get_data(){

        try {
            cursor = database.rawQuery("SELECT *  FROM new_project", null);
            name_index = cursor.getColumnIndex("name");
            text_color_index = cursor.getColumnIndex("color_text");
            bacround_color_index = cursor.getColumnIndex("color_bacround");
            icon_index = cursor.getColumnIndex("icon");
            status_index = cursor.getColumnIndex("status");
            time_text_index = cursor.getColumnIndex("time_text");
            kataqori_index = cursor.getColumnIndex("kataqori");
            time_start_index = cursor.getColumnIndex("time_start");


            while (cursor.moveToNext()) {

                if(cursor.getString(status_index).equals("false")){
                    proje_namee.add(cursor.getString(name_index));
                    text_color.add(cursor.getInt(text_color_index));
                    bacround_color.add(cursor.getInt(bacround_color_index));
                    byte[] icon_byte = cursor.getBlob(icon_index);
                    Bitmap icon_bitmap = BitmapFactory.decodeByteArray(icon_byte, 0, icon_byte.length);
                    icon.add(icon_bitmap);
                    status.add(cursor.getString(status_index));
                    get_time_text.add(cursor.getString(time_text_index));
                    katogori.add(cursor.getString(kataqori_index));
                    get_time_text.add(cursor.getString(time_text_index));
                   // System.out.println("timer" +cursor.getString(time_text_index));

                }else if (cursor.getString(status_index).equals("true")){

                    projename_run.add(cursor.getString(name_index));
                   get_time_text.add(cursor.getString(time_text_index));
                    textcolor_run.add(cursor.getInt(text_color_index));
                    bacroundcolor_run.add(cursor.getInt(bacround_color_index));
                    byte[] icon_byte = cursor.getBlob(icon_index);
                    Bitmap icon_bitmap = BitmapFactory.decodeByteArray(icon_byte, 0, icon_byte.length);
                    icon_run.add(icon_bitmap);
                    get_time_text_run.add(cursor.getString(time_text_index));
                    katogori_run.add(cursor.getString(kataqori_index));
                    time_start.add(cursor.getString(time_start_index));
                    total_time.add(0L);
                    run_numbers++;
                    System.out.println("timer "+ cursor.getString(time_start_index));
                }

            }
            cursor.close();
        }catch (Exception e){
            //System.out.println("bitmap new eror");
        }
    }
    public  void run_listview_animation(){
        if(projename_run.size() ==0){
            animationView.setVisibility(View.VISIBLE);
        }else{
            animationView.setVisibility(View.INVISIBLE);
        }
    }

    public  void run_adapter(){

      adabter_run =new Adabter_run(getActivity() , projename_run , textcolor_run , bacroundcolor_run , icon_run , get_time_text_run , katogori_run);
        run_listview.setAdapter(adabter_run);
    }

    public void  adapter_all(){
        adapter = new Adapter(getActivity() , proje_namee , text_color , bacround_color , status , icon , katogori);
        all_list_vievw.setAdapter(adapter);

    }

    public void project_clear(){
        proje_namee.clear();
        text_color.clear();
        projename_run.clear();
        textcolor_run.clear();
        bacround_color.clear();
        bacroundcolor_run.clear();
        time_start.clear();
        icon.clear();
        icon_run.clear();
        status.clear();
    }

    public void get_runmax_dialog(){
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.run_max_artel);
        Button run_max_btn = dialog.findViewById(R.id.run_max_btn);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.show();
        run_max_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public  void add_new_timeproject(String name , String katogori ,  long total , Long start , Bitmap bitmap){
        String sqlSorgu = "INSERT INTO timer_project (name  , kataqori ,  total  ,time_start , icon) VALUES(?,? ,? , ? ,?)";

        try {
            SQLiteStatement statement = database.compileStatement(sqlSorgu);
            statement.bindString(1,name);
            statement.bindString(2,katogori);
            statement.bindLong(3,total);
            statement.bindLong(4, start);
            statement.bindBlob(5, convertBitmapToByteArrayUncompressed(bitmap));


            statement.execute();
            System.out.println("data add succes time project");
        }catch (Exception e){
            System.out.println("data addd eror");
        }

    }




    public  byte[] convertBitmapToByteArrayUncompressed(Bitmap bitmap){
        ByteBuffer byteBuffer = ByteBuffer.allocate(bitmap.getByteCount());
        bitmap.copyPixelsToBuffer(byteBuffer);
        byteBuffer.rewind();
        return byteBuffer.array();
    }
}