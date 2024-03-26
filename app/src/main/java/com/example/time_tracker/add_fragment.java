package com.example.time_tracker;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import yuku.ambilwarna.AmbilWarnaDialog;


public class add_fragment extends Fragment {

    SQLiteDatabase database;
    // Bitmap sellect_image;
    String end_id = "calendar";
Dialog dialog;
    Runnable runnable;
    Handler handler;
    String katogori = "kalendar";
    ArrayList<String> name_deneme= new ArrayList<>();


    BottomNavigationView navigationView;

    LottieAnimationView animationView;
    Button backrount_color_btn , text_color_btn,add_add_btn ;
    CardView cardviev_deneme;
    TextView name_text , time_text  ;
    EditText  project_name_text;
    ImageView add_image_icon , img_bag , img_hat , img_lamp , img_gym , img_plane , img_bovlinq , img_clock , img_chhese , img_facebook , img_runing , img_bank , img_java , img_cofee , img_shopping , img_curassan;
    int defaufl_color = Color.YELLOW , default_text_color =Color.WHITE;
    boolean text_white = true;
    Cursor cursor;
    int name_index , text_color_index , bacround_color_index , image_index;

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_fragment, container, false);

        defaufl_color = ContextCompat.getColor(getActivity() , R.color.orange);

        backrount_color_btn = v.findViewById(R.id.backround_color_btn);
        add_add_btn = v.findViewById(R.id.add_add_btn);
        project_name_text = v.findViewById(R.id.project_name_text);
        cardviev_deneme = v.findViewById(R.id.cardView_deneme);
        name_text = v.findViewById(R.id.name_text);
        text_color_btn = v.findViewById(R.id.text_color_btn);
        img_bag = v.findViewById(R.id.img_bag);
        img_java = v.findViewById(R.id.img_java);
        img_lamp = v.findViewById(R.id.img_lamb);
        img_facebook = v.findViewById(R.id.img_facebook);
        img_curassan = v.findViewById(R.id.img_curassan);
        img_bank = v.findViewById(R.id.img_bank);
        img_cofee = v.findViewById(R.id.img_cofee);
        img_shopping = v.findViewById(R.id.img_shopping);
        img_gym = v.findViewById(R.id.img_gym);
        img_plane = v.findViewById(R.id.img_plane);
        img_runing = v.findViewById(R.id.img_run);
        img_bovlinq = v.findViewById(R.id.img_bovlink);
        img_clock = v.findViewById(R.id.img_clock);
        img_chhese = v.findViewById(R.id.img_chese);
        img_hat = v.findViewById(R.id.img_hat);
//        navigationView = v.findViewById(R.id.bottomNavigationView);

        time_text = v.findViewById(R.id.time_text);
        add_image_icon = v.findViewById(R.id.add_image_icon);

        animationView = v.findViewById(R.id.play_design);
       // animationView.pauseAnimation();


        try {
                  database = getActivity().openOrCreateDatabase("Oracle" , Context.MODE_PRIVATE,null);
              //    System.out.println("succes");
           }catch (Exception e){
              Toast.makeText(getActivity(), String.valueOf(e), Toast.LENGTH_LONG).show();
            System.out.println(e);
           }

               handler = new Handler();
                runnable  =new Runnable() {
                   @Override
                   public void run() {

                     if(!TextUtils.isEmpty(project_name_text.getText())){
                     name_text.setText(project_name_text.getText());
                   }

             handler.postDelayed(runnable  ,100);
         }
     };handler.post(runnable);




add_add_btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        read_sql();

        if(!name_deneme.contains(String.valueOf(name_text.getText()))){

         byte[] bytes = conver_bitmap(add_image_icon);

            try {
                String sqlSorgu = "INSERT INTO new_project (name  , color_text  , color_bacround , icon , status  , kataqori , time_text , time_start) VALUES(?,? , ?,? , ? , ? , ? , ?)";
                SQLiteStatement statement = database.compileStatement(sqlSorgu);
                statement.bindString(1,String.valueOf(project_name_text.getText()));
                statement.bindLong(2,default_text_color);
                statement.bindLong(3,defaufl_color);
                statement.bindBlob(4,bytes);
                statement.bindString(5 , "false");
                statement.bindString(6 , katogori);
                statement.bindString(7 , "00.00.00");
                statement.bindLong(8 , 0L);

                statement.execute();

            }catch (Exception e){
                System.out.println(e);
               // System.out.println("sucess add");
            }

        try {

            get_add_succes_dialog();
                Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Fragment fragment = new time_fragment();
                        dialog.dismiss();
                        replacefragment(fragment);
                        //navigationView.setSelectedI(R.id.time_menu);
                       // navigationView.getMenu().findItem(R.id.time_menu).setChecked(true);
                    }
                };handler.postDelayed(runnable ,2000);



            }catch (Exception e){
                System.out.println(e);
            }

        }else{
            get_eror_dialog();
        }

    }
});

        img_bag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               setimage_bbtn("work");
            }
        });


        img_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setimage_bbtn("shopping");

            }
        });
        img_curassan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setimage_bbtn("croissan");
            }
        });
        img_java.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setimage_bbtn("java");
            }
        });

        img_cofee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setimage_bbtn("cofee");
            }
        });
        img_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setimage_bbtn("bank");
            }
        });

        img_chhese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setimage_bbtn("cheese");
            }
        });



        img_clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setimage_bbtn("clock");
            }
        });


        img_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setimage_bbtn("facebook");
            }
        });


        img_bovlinq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setimage_bbtn("bovlinq");
            }
        });

        img_runing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setimage_bbtn("run");
            }
        });

        img_plane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setimage_bbtn("plane");
            }
        });

        img_hat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setimage_bbtn("hat");
            }
        });

        img_lamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setimage_bbtn("lamp");
            }
        });

        img_gym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setimage_bbtn("gym");
            }
        });




       backrount_color_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AmbilWarnaDialog dialog = new AmbilWarnaDialog(getContext(), defaufl_color, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        // color is the color selected by the user.
                        defaufl_color = color;
                        cardviev_deneme.setCardBackgroundColor(defaufl_color);

                    }

                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                        // cancel was selected by the user

                    }
                });
                dialog.show();
            }

        });

       text_color_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(text_white){
                   default_text_color = Color.BLACK;
                   name_text.setTextColor(Color.BLACK);
                   text_white =false;
                   animationView.setAnimation(R.raw.time_black);
                   setimage_bbtn(end_id);
                   time_text.setTextColor(Color.BLACK);
               }else{
                   name_text.setTextColor(Color.WHITE);
                   default_text_color = Color.WHITE;
                   text_white =true;
                   animationView.setAnimation(R.raw.timer);
                   setimage_bbtn(end_id);
                  // add_image_icon.setColorFilter(Color.WHITE);
                   time_text.setTextColor(Color.WHITE);
               }
           }
       });

        return  v;
    }

    public  byte[] conver_bitmap(ImageView imageView){
        try {
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);

           // System.out.println("sucess bitmap");
            return stream.toByteArray();
        }catch (Exception e){
           // System.out.println("eror bitmap");
            System.out.println(e);
            return null;
        }

    }

    public void setimage_bbtn(String id){
        switch (id){

            case "bank":
                katogori = "bank";
                if(default_text_color == Color.WHITE){
                    add_image_icon.setImageResource(R.drawable.bank_white);

                }else
                {
                    add_image_icon.setImageResource(R.drawable.bank_black);
                }
                break;
            case "lamp":
                katogori = "lamp";
                if(default_text_color == Color.WHITE){
                    add_image_icon.setImageResource(R.drawable.lap_white);

                }else
                {
                    add_image_icon.setImageResource(R.drawable.lap_black);
                }
                break;

            case "java":
                katogori = "java";

                if(default_text_color == Color.WHITE){
                    add_image_icon.setImageResource(R.drawable.java_white);

                }else
                {
                    add_image_icon.setImageResource(R.drawable.java_black);
                }
                break;

            case "run":
                katogori = "run";
                if(default_text_color == Color.WHITE){
                    add_image_icon.setImageResource(R.drawable.run_white);

                }else
                {
                    add_image_icon.setImageResource(R.drawable.run_black);
                }
                break;

            case "shopping":
                katogori = "shopping";
                if(default_text_color == Color.WHITE){
                    add_image_icon.setImageResource(R.drawable.shopping_white);

                }else
                {
                    add_image_icon.setImageResource(R.drawable.shoppin_black);
                }
                break;

            case "bovlinq":
                katogori = "bovlinq";
                if(default_text_color == Color.WHITE){
                    add_image_icon.setImageResource(R.drawable.bovlink_white);

                }else
                {
                    add_image_icon.setImageResource(R.drawable.bovlink_black);
                }
                break;

            case "cofee":
                katogori = "cofee";
                if(default_text_color == Color.WHITE){
                    add_image_icon.setImageResource(R.drawable.cofee_white);

                }else
                {
                    add_image_icon.setImageResource(R.drawable.cofee_black);
                }
                break;

            case "croissan":
                katogori = "croissan";
                if(default_text_color == Color.WHITE){
                    add_image_icon.setImageResource(R.drawable.croissan_white);

                }else
                {
                    add_image_icon.setImageResource(R.drawable.croissan_black);
                }
                break;

            case "plane":
                katogori = "plane";
                if(default_text_color == Color.WHITE){
                    add_image_icon.setImageResource(R.drawable.plane_white);

                }else
                {
                    add_image_icon.setImageResource(R.drawable.plane_black);
                }
                break;

            case "facebook":
                katogori = "facebook";
                if(default_text_color == Color.WHITE){
                    add_image_icon.setImageResource(R.drawable.facebook_white);

                }else
                {
                    add_image_icon.setImageResource(R.drawable.facebook_black);
                }
                break;

            case "clock":
                katogori = "clock";
                if(default_text_color == Color.WHITE){
                    add_image_icon.setImageResource(R.drawable.clock_white);

                }else
                {
                    add_image_icon.setImageResource(R.drawable.clock_black);
                }
                break;

            case "cheese":
                katogori = "cheese";
                if(default_text_color == Color.WHITE){
                    add_image_icon.setImageResource(R.drawable.chess_white);

                }else
                {
                    add_image_icon.setImageResource(R.drawable.chese_black);
                }
                break;

            case "gym":
                katogori = "gym";
                if(default_text_color == Color.WHITE){
                    add_image_icon.setImageResource(R.drawable.gym_white);

                }else
                {
                    add_image_icon.setImageResource(R.drawable.gym_black);
                }
                break;


            case "hat":
                katogori = "hat";
                if(default_text_color == Color.WHITE){
                    add_image_icon.setImageResource(R.drawable.hat_white);

                }else
                {
                    add_image_icon.setImageResource(R.drawable.hat_black);
                }
                break;

            case "work":
                katogori = "work";
                if(default_text_color == Color.WHITE){
                    add_image_icon.setImageResource(R.drawable.work_white);

                }else
                {
                    add_image_icon.setImageResource(R.drawable.work_black);
                }
                break;

            case "calendar":
                katogori = "calendar";
                if(default_text_color == Color.WHITE){
                    add_image_icon.setImageResource(R.drawable.calendar_white);

                }else
                {
                    add_image_icon.setImageResource(R.drawable.calendar_black);
                }
                break;




         }
            end_id =id;
    }

    void replacefragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction =fragmentManager.beginTransaction();
        transaction.replace(R.id.activity_layout, fragment);
        transaction.commit();
    }

    public void get_add_succes_dialog(){
       dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.add_success_artel);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
    }

    public void get_eror_dialog(){
       dialog = new Dialog(getActivity());

        dialog.setContentView(R.layout.eror_artel);
        Button eror_btn = dialog.findViewById(R.id.eror_btn);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.show();

        eror_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public  void read_sql(){
        cursor = database.rawQuery("SELECT *  FROM new_project", null);
        name_index= cursor.getColumnIndex("name");

        while (cursor.moveToNext()){
            name_deneme.add(cursor.getString(name_index));
            //  System.out.println(cursor.getString(name_index));
        }cursor.close();
    }
}
