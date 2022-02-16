package com.unknown.proj;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.airbnb.lottie.LottieAnimationView;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class AutoScrollAdapter extends SliderViewAdapter<AutoScrollAdapter.Holder> {

    Drawable[] images;
    Activity activity;

    public AutoScrollAdapter(Drawable[] images, Activity activity) {
        this.images = images;
        this.activity = activity;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slider_item, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {

        viewHolder.LAnimView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        viewHolder.LAnimView.setImageDrawable(images[position]);
        if (images[position] == null) {
            viewHolder.LAnimView.setAnimation("banner_loading2.json");
            viewHolder.LAnimView.loop(true);
            viewHolder.LAnimView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        //View v = new View(viewHolder.itemView.getContext());

        viewHolder.LAnimView.setOnClickListener(view -> setClickListeners(view, position, activity));

    }

    private void setClickListeners(View view, int pos, Activity activity) {

        FragmentManager ft = ((FragmentActivity) activity).getSupportFragmentManager();

        if (pos == 0) {
           ft.beginTransaction().replace(R.id.fragmentContainerView, new sec_frag()).commit();
        }
        if (pos == 1) {
            ft.beginTransaction().replace(R.id.fragmentContainerView, new third_frag()).commit();
        }
        if (pos == 2) {

        }

    }

    @Override
    public int getCount() {
        return images.length;
    }

    public class Holder extends ViewHolder {

        LottieAnimationView LAnimView;

        public Holder(View itemView) {
            super(itemView);
            LAnimView = itemView.findViewById(R.id.ban);
        }

    }
}
















