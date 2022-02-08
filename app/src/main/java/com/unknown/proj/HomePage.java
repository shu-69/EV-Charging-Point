package com.unknown.proj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

public class HomePage extends AppCompatActivity {

    SharedPreferences VehicleDetails;
    SharedPreferences.Editor editor;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        VehicleDetails = getSharedPreferences("VehicleDetails", MODE_PRIVATE);
        editor = VehicleDetails.edit();

        getVehicleImages();

        BottomNavigationView bottom_nav = findViewById(R.id.bottomnav);
        bottom_nav.setOnNavigationItemSelectedListener(navListner);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new first_frag()).commit();

    }

    private void getVehicleImages() {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imageRef1 = storage.getReference().child("vehicle_images").child("vehicle1.png");
        imageRef1.getBytes(1024 * 1024)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        try {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            editor.putString("i1", BitMapToString(bitmap));
//                            FileOutputStream out = openFileOutput("banner1", MODE_PRIVATE);
//                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//                            out.flush();
//                            out.close();
                        } catch (Exception e) {
                        }
                    }

                });
        StorageReference imageRef2 = storage.getReference().child("vehicle_images").child("vehicle2.png");
        imageRef2.getBytes(1024 * 1024)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        try {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            editor.putString("i2", BitMapToString(bitmap));
//                            FileOutputStream out = openFileOutput("banner1", MODE_PRIVATE);
//                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//                            out.flush();
//                            out.close();
                        } catch (Exception e) {
                        }
                    }

                });
        StorageReference imageRef3 = storage.getReference().child("vehicle_images").child("vehicle3.png");
        imageRef3.getBytes(1024 * 1024)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        try {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            editor.putString("i3", BitMapToString(bitmap));
//                            FileOutputStream out = openFileOutput("banner1", MODE_PRIVATE);
//                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//                            out.flush();
//                            out.close();
                        } catch (Exception e) {
                        }
                    }

                });
        StorageReference imageRef4 = storage.getReference().child("vehicle_images").child("vehicle4.png");
        imageRef4.getBytes(1024 * 1024)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        try {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            editor.putString("i4", BitMapToString(bitmap));
//                            FileOutputStream out = openFileOutput("banner1", MODE_PRIVATE);
//                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//                            out.flush();
//                            out.close();
                        } catch (Exception e) {
                        }
                    }

                });

        editor.apply();

        getVehicleNameAndDesc();

    }

    private void getVehicleNameAndDesc(){
        db.collection("vehicle_details").document("vehicle1").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                editor.putString("n1", snapshot.getString("VehicleName"));
                editor.putString("d1", snapshot.getString("VehicleDescription"));
                editor.putString("l1", snapshot.getString("Link"));
            }
        });

        db.collection("vehicle_details").document("vehicle2").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                editor.putString("n2", snapshot.getString("VehicleName"));
                editor.putString("d2", snapshot.getString("VehicleDescription"));
                editor.putString("l2", snapshot.getString("Link"));
            }
        });

        db.collection("vehicle_details").document("vehicle3").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                editor.putString("n3", snapshot.getString("VehicleName"));
                editor.putString("d3", snapshot.getString("VehicleDescription"));
                editor.putString("l3", snapshot.getString("Link"));
            }
        });

        db.collection("vehicle_details").document("vehicle4").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                editor.putString("n4", snapshot.getString("VehicleName"));
                editor.putString("d4", snapshot.getString("VehicleDescription"));
                editor.putString("l4", snapshot.getString("Link"));
            }
        });

        editor.apply();
    }

    public String BitMapToString(Bitmap bitmap) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListner = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()){
                case R.id.first_frag:
                    selectedFragment = new first_frag();
                    break;

                case R.id.sec_frag:_frag:
                selectedFragment = new sec_frag();
                    break;

                case R.id.third_frag:t_frag:
                selectedFragment = new third_frag();
                    break;
//
//                case R.id.fourth_frag:_frag:
//                selectedFragment = new fourth_frag();
//                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, selectedFragment).commit();

            return true;
        }
    };

}