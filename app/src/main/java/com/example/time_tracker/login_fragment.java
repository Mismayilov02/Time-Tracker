package com.example.time_tracker;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;


public class login_fragment extends Fragment {

    EditText userid_text , password_text;
    Dialog dialog;
    Button login_button;
    ImageView imageView;

boolean eye_bolean = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login_fragment, container, false);

        login_button = v.findViewById(R.id.login_button);
        password_text = v.findViewById(R.id.password_text);
        userid_text = v.findViewById(R.id.user_text);
        imageView = v.findViewById(R.id.image_hide);


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( true/*!TextUtils.isEmpty(String.valueOf(password_text.getText())) && !TextUtils.isEmpty(String.valueOf(userid_text.getText()))*/) {
                    get_succes_dialog();
                    Handler handler = new Handler();
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {

                            Intent intent = new Intent(getActivity(), menu_activity.class);
                            dialog.dismiss();
                            startActivity(intent);

                        }
                    };
                    handler.postDelayed(runnable, 2000);
                }else{
                    userid_text.setHintTextColor(Color.parseColor("#C1C66A6A"));
                    password_text.setHintTextColor(Color.parseColor("#C1C66A6A"));
                }

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(eye_bolean){
                    imageView.setImageResource(R.drawable.eye_show);
                    eye_bolean = false;
                    password_text.setTransformationMethod(null);
                }else
                {
                    imageView.setImageResource(R.drawable.eye_hide);
                    password_text.setTransformationMethod(new PasswordTransformationMethod());
                    eye_bolean= true;
                }
            }
        });


        return v;
    }

   public void get_succes_dialog(){
         dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.succes_artel);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
    }
}