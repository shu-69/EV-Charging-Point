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
//        if (images[position] == null) {
//            viewHolder.LAnimView.setAnimation("banner_loading2.json");
//            viewHolder.LAnimView.loop(true);
//            viewHolder.LAnimView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        }
        //View v = new View(viewHolder.itemView.getContext());

    }

    private void setClickListeners(View view, int pos) {

//        locationSP = view.getContext().getSharedPreferences("Location", Context.MODE_PRIVATE);
//        if (pos == 0) {
//            if (locationSP.getString("city", "").isEmpty() || locationSP.getString("state", "").isEmpty() || locationSP.getString("country", "").isEmpty()) {
//                Intent intent = new Intent(view.getContext(), location.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                intent.putExtra("destiny", "next");
//                view.getContext().startActivity(intent);
//            } else {
//                Intent intent = new Intent(view.getContext(), allHospitals.class);
//                view.getContext().startActivity(intent);
//            }
//        }
//        if (pos == 1) {
//            view.getContext().startActivity(new Intent(view.getContext(), ambulanceBooking.class));
//        }
//        if (pos == 2) {
//            view.getContext().startActivity(new Intent(view.getContext(), healthInsu.class));
//        }
//        if (pos == 3) {
//            view.getContext().startActivity(new Intent(view.getContext(), invite.class));
//        }
//        if (pos == 4) {
//            view.getContext().startActivity(new Intent(view.getContext(), helpAndSupport.class));
//        }
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
















