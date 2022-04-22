package com.example.time_tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class menu_activity extends AppCompatActivity {

    BottomNavigationView navigationView;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

         navigationView = findViewById(R.id.bottomNavigationView);
        fragment = new time_fragment();
        setFragment(fragment);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.add_menu:
                        fragment = new add_fragment();
                        setFragment(fragment);
                        return true;
                    case R.id.data_menu:
                        fragment = new piec_fragment();
                        setFragment(fragment);
                        return true;

                    case R.id.time_menu:
                        fragment = new time_fragment();
                        setFragment(fragment);
                        return true;


                    default: return  false;
                }
                // getSupportFragmentManager().beginTransaction().replace(R.id.framelayout , fragment).commit();

            }
        });
    }

    void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activity_layout, fragment);
        fragmentTransaction.commit();
    }
}