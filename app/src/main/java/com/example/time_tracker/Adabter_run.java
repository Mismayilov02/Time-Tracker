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

public class Adabter_run extends ArrayAdapter<String> {
    ArrayList<String> stiring = new ArrayList<>();

   public ArrayList<String> projename_run = new ArrayList<>();
    ArrayList<Integer> textcolor_run = new ArrayList<>();
    ArrayList<Integer> bacroundcolor_run = new ArrayList<>();
    ArrayList<Bitmap>icon_run = new ArrayList<>();
  public   ArrayList<String> catogori_text = new ArrayList<>();
    public ArrayList<TextView> time_text_run =new ArrayList<>();
   public ArrayList<LottieAnimationView> animation_progres_run = new ArrayList<>();
    Context context;
    int White = Color.WHITE;
    LottieAnimationView play_animation , progress_animation;
    public Adabter_run(Context context , ArrayList<String> projename_run , ArrayList<Integer> textcolor_run , ArrayList<Integer> bacroundcolor_run  ,ArrayList<Bitmap> icon_run , ArrayList<String> time_text_run, ArrayList<String>catogori_text){
        super(context , R.layout.timer_run_design , projename_run);
        this.bacroundcolor_run = bacroundcolor_run;
        this.icon_run =icon_run;
        this.projename_run = projename_run;
        this.textcolor_run = textcolor_run;
        this.catogori_text = catogori_text;
      //  this.time_text_run =time_text_run;
        this.context = context;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View vi = LayoutInflater.from(context).inflate(R.layout.timer_run_design , null);

      //  System.out.println("view run succes");
        CardView cardView = vi.findViewById(R.id.cardview_design);
        ImageView set_icon_image = vi.findViewById(R.id.icon_design);
        play_animation = vi.findViewById(R.id.play_design);
        progress_animation = vi.findViewById(R.id.progress_design);
        play_animation.pauseAnimation();
        progress_animation.playAnimation();
        play_animation.setVisibility(View.INVISIBLE);
        TextView set_project_name_text = vi.findViewById(R.id.text_design);
        TextView timer = vi.findViewById(R.id.timer_design);
        if(textcolor_run.get(position) == White){
            progress_animation.setAnimation(R.raw.timer);
        }else{
            progress_animation.setAnimation(R.raw.time_black);
        }
        System.out.println(bacroundcolor_run.get(position));

        cardView.setCardBackgroundColor(bacroundcolor_run.get(position));
        set_project_name_text.setText((String)projename_run.get(position));
        set_project_name_text.setTextColor(textcolor_run.get(position));
        set_icon_image.setImageBitmap(icon_run.get(position));
        timer.setTextColor(textcolor_run.get(position));
        animation_progres_run.add(play_animation);
        time_text_run.add(timer);


        return vi;
    }
}
