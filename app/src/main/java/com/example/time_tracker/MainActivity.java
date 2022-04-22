package com.example.time_tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageView Imageview;
    Animation imageAnimation;
    SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Imageview = findViewById(R.id.imageView);
      imageAnimation = AnimationUtils.loadAnimation(this, R.anim.image_animation);

        try {
            database = this.openOrCreateDatabase("Oracle", MODE_PRIVATE, null);
            database.execSQL("CREATE TABLE IF NOT EXISTS new_project(name VARCHAR2 , color_text INTEGER , color_bacround INTEGER , icon BLOB , status VARCHAR2 , kataqori VARCHAR2 , time_start VARCHAR2 , time_text VARCHAR2)");
            database.execSQL("CREATE TABLE IF NOT EXISTS timer_project(name VARCHAR2 , icon BLOB , kataqori  VARCHAR2 , time_start LONG , total LONG)");

           // System.out.println("sucesscmain activty");
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), String.valueOf(e), Toast.LENGTH_LONG).show();
            System.out.println(e);
        }



        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
              Fragment fragment = new login_fragment();
               replacefragment(fragment);
            }
        }; handler.postDelayed(runnable,5000);

       Imageview.startAnimation(imageAnimation);
    }


    public  void  replacefragment(Fragment fragment){
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction transaction =fragmentManager.beginTransaction();
        transaction.replace(R.id.open_layout, fragment);
        transaction.commit();
    }
}