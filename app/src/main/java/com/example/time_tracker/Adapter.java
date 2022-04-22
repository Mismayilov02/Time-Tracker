package com.example.time_tracker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;

public class Adapter extends ArrayAdapter<String> {

 public ArrayList<String> project_name = new ArrayList<>();
   ArrayList<Integer>text_color = new ArrayList<>();
  ArrayList<Integer> bacround_color = new ArrayList<>();
 public ArrayList<LottieAnimationView> animation_play = new ArrayList<>();
 public ArrayList<TextView> timer_text = new ArrayList<TextView>();
 public ArrayList<String> catogori_text = new ArrayList<>();

    ArrayList<Bitmap> iconn = new ArrayList<>();
    ArrayList<String> status = new ArrayList<>();
    Context context;
    int white =Color.WHITE;


    int set_text_color , set_bacround_color;

    LottieAnimationView play_animation , progress_animation;
    TextView  set_project_name_text;
    TextView timer;

    public Adapter(@NonNull Context context,ArrayList<String> project_name, ArrayList<Integer> text_color, ArrayList<Integer> bacround_color, ArrayList<String> status , ArrayList<Bitmap> icon , ArrayList<String>catogori_text) {
        super(context, R.layout.timer_run_design ,R.id.text_design,project_name );
        this.project_name = project_name;
        this.text_color = text_color;
        this.bacround_color = bacround_color;
        this.iconn = icon;
        this.status = status;
        this.catogori_text = catogori_text;
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View vi = LayoutInflater.from(context).inflate(R.layout.timer_run_design , null);

       // System.out.println("view succes");
             CardView cardView = vi.findViewById(R.id.cardview_design);
            ImageView set_icon_image = vi.findViewById(R.id.icon_design);
               play_animation = vi.findViewById(R.id.play_design);
               progress_animation = vi.findViewById(R.id.progress_design);
               timer = vi.findViewById(R.id.timer_design);
               play_animation.pauseAnimation();
               progress_animation.pauseAnimation();
               play_animation.setVisibility(View.VISIBLE);
               set_project_name_text = vi.findViewById(R.id.text_design);
               timer.setVisibility(View.INVISIBLE);

               animation_play.add(play_animation);


            timer_text.add(timer);
            if(text_color.get(position) == white){
                progress_animation.setAnimation(R.raw.timer);
            }else{
                progress_animation.setAnimation(R.raw.time_black);
            }

              cardView.setCardBackgroundColor(bacround_color.get(position));
              set_project_name_text.setText((String)project_name.get(position));
              set_project_name_text.setTextColor(text_color.get(position));
               set_icon_image.setImageBitmap(iconn.get(position));
               timer.setTextColor(text_color.get(position));
               timer.setText("00.00.00");


        return vi;
    }
}
