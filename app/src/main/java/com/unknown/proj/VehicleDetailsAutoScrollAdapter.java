package com.unknown.proj;

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
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.card.MaterialCardView;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class VehicleDetailsAutoScrollAdapter extends SliderViewAdapter<VehicleDetailsAutoScrollAdapter.Holder> {

    Bitmap[] images;
    String[] names;
    String[] descriptions;
    String[] links;

    public VehicleDetailsAutoScrollAdapter(Bitmap[] images, String[] names, String[] descriptions, String[] links) {
        this.images = images;
        this.names = names;
        this.descriptions = descriptions;
        this.links = links;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vehicle_details_banner_card, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {

        viewHolder.VehicleImage.setImageBitmap(images[position]);
        viewHolder.VehicleName.setText(names[position]);
        viewHolder.VehicleDescription.setText(descriptions[position]);

        viewHolder.ParentCard.setOnClickListener(v -> setClickListeners(v, position));

    }

    private void setClickListeners(View view, int pos) {

        if (pos == 0) {
           Intent intent = new Intent(view.getContext(), WebView.class);
           intent.putExtra("url", links[pos]);
           view.getContext().startActivity(intent);
        }
        if (pos == 1) {
            Intent intent = new Intent(view.getContext(), WebView.class);
            intent.putExtra("url", links[pos]);
            view.getContext().startActivity(intent);
        }
        if (pos == 2) {
            Intent intent = new Intent(view.getContext(), WebView.class);
            intent.putExtra("url", links[pos]);
            view.getContext().startActivity(intent);
        }
        if (pos == 3) {
            Intent intent = new Intent(view.getContext(), WebView.class);
            intent.putExtra("url", links[pos]);
            view.getContext().startActivity(intent);
        }
    }

    @Override
    public int getCount() {
        return images.length;
    }

    public class Holder extends ViewHolder {

        MaterialCardView ParentCard;
        ImageView VehicleImage;
        TextView VehicleName, VehicleDescription;

        public Holder(View itemView) {
            super(itemView);
            ParentCard = itemView.findViewById(R.id.vehicle_details_card);
            VehicleImage = itemView.findViewById(R.id.vehicle_image);
            VehicleName = itemView.findViewById(R.id.vehicle_name_text);
            VehicleDescription = itemView.findViewById(R.id.vehicle_desc_text);
        }

    }
}
















