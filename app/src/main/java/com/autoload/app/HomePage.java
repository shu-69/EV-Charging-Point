package com.autoload.app;

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

public class HomePage extends AppCompatActivity {

    SharedPreferences VehicleDetails, TopCompaniesLink, NewLaunchesDetails;
    SharedPreferences.Editor editor_vehicleDetails, editor_topCompanies, editor_newLaunches;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static BottomNavigationView bottom_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        VehicleDetails = getSharedPreferences("VehicleDetails", MODE_PRIVATE);
        TopCompaniesLink = getSharedPreferences("TopCompaniesLink", MODE_PRIVATE);
        NewLaunchesDetails = getSharedPreferences("NewLaunchesDetails", MODE_PRIVATE);
        editor_vehicleDetails = VehicleDetails.edit();
        editor_topCompanies = TopCompaniesLink.edit();
        editor_newLaunches = NewLaunchesDetails.edit();

//        getVehicleImages();
//        getTopCompaniesLink();
//        getNewLaunches();

        bottom_nav = findViewById(R.id.bottomnav);
        bottom_nav.setOnNavigationItemSelectedListener(navListner);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new first_frag()).commit();

    }

    private void getNewLaunches() {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imageRef1 = storage.getReference().child("new_launches").child("image1.png");
        imageRef1.getBytes(1024 * 1024)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        try {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            editor_newLaunches.putString("i1", BitMapToString(bitmap));
                            editor_newLaunches.apply();
                        } catch (Exception e) {
                        }
                    }

                });

        StorageReference imageRef2 = storage.getReference().child("new_launches").child("image2.png");
        imageRef2.getBytes(1024 * 1024)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        try {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            editor_newLaunches.putString("i2", BitMapToString(bitmap));
                            editor_newLaunches.apply();
                        } catch (Exception e) {
                        }
                    }

                });

        StorageReference imageRef3 = storage.getReference().child("new_launches").child("image3.png");
        imageRef3.getBytes(1024 * 1024)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        try {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            editor_newLaunches.putString("i3", BitMapToString(bitmap));
                            editor_newLaunches.apply();
                        } catch (Exception e) {
                        }
                    }

                });

        StorageReference imageRef4 = storage.getReference().child("new_launches").child("image4.png");
        imageRef4.getBytes(1024 * 1024)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        try {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            editor_newLaunches.putString("i4", BitMapToString(bitmap));
                            editor_newLaunches.apply();
                        } catch (Exception e) {
                        }
                    }

                });

        StorageReference imageRef5 = storage.getReference().child("new_launches").child("image5.png");
        imageRef5.getBytes(1024 * 1024)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        try {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            editor_newLaunches.putString("i5", BitMapToString(bitmap));
                            editor_newLaunches.apply();
                        } catch (Exception e) {
                        }
                    }

                });

        StorageReference imageRef6 = storage.getReference().child("new_launches").child("image6.png");
        imageRef6.getBytes(1024 * 1024)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        try {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            editor_newLaunches.putString("i6", BitMapToString(bitmap));
                            editor_newLaunches.apply();
                        } catch (Exception e) {
                        }
                    }

                });

        db.collection("new_launches_details").document("Info1").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                editor_newLaunches.putString("t1", snapshot.getString("Title"));
                editor_newLaunches.putString("l1", snapshot.getString("Link"));

                editor_newLaunches.apply();
            }
        });

        db.collection("new_launches_details").document("Info2").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                editor_newLaunches.putString("t2", snapshot.getString("Title"));
                editor_newLaunches.putString("l2", snapshot.getString("Link"));

                editor_newLaunches.apply();
            }
        });

        db.collection("new_launches_details").document("Info3").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                editor_newLaunches.putString("t3", snapshot.getString("Title"));
                editor_newLaunches.putString("l3", snapshot.getString("Link"));

                editor_newLaunches.apply();
            }
        });

        db.collection("new_launches_details").document("Info4").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                editor_newLaunches.putString("t4", snapshot.getString("Title"));
                editor_newLaunches.putString("l4", snapshot.getString("Link"));

                editor_newLaunches.apply();
            }
        });

        db.collection("new_launches_details").document("Info5").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                editor_newLaunches.putString("t5", snapshot.getString("Title"));
                editor_newLaunches.putString("l5", snapshot.getString("Link"));

                editor_newLaunches.apply();
            }
        });

        db.collection("new_launches_details").document("Info6").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                editor_newLaunches.putString("t6", snapshot.getString("Title"));
                editor_newLaunches.putString("l6", snapshot.getString("Link"));

                editor_newLaunches.apply();
            }
        });
    }

    private void getTopCompaniesLink() {
        db.collection("top_companies_info").document("Links").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                editor_topCompanies.putString("link1", snapshot.getString("Link1"));
                editor_topCompanies.putString("link2", snapshot.getString("Link2"));
                editor_topCompanies.putString("link3", snapshot.getString("Link3"));
                editor_topCompanies.putString("link4", snapshot.getString("Link4"));
                editor_topCompanies.putString("link5", snapshot.getString("Link5"));
                editor_topCompanies.putString("link6", snapshot.getString("Link6"));

                editor_topCompanies.apply();
            }
        });
    }

    private void getVehicleImages() {

        getVehicleNameAndDesc();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imageRef1 = storage.getReference().child("vehicle_images").child("vehicle1.png");
        imageRef1.getBytes(1024 * 1024)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        try {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            editor_vehicleDetails.putString("i1", BitMapToString(bitmap));
                            editor_vehicleDetails.apply();
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
                            editor_vehicleDetails.putString("i2", BitMapToString(bitmap));
                            editor_vehicleDetails.apply();
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
                            editor_vehicleDetails.putString("i3", BitMapToString(bitmap));
                            editor_vehicleDetails.apply();
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
                            editor_vehicleDetails.putString("i4", BitMapToString(bitmap));
                            editor_vehicleDetails.apply();
                        } catch (Exception e) {
                        }
                    }

                });

    }

    private void getVehicleNameAndDesc() {
        db.collection("vehicle_details").document("Vehicle1").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                editor_vehicleDetails.putString("n1", snapshot.getString("VehicleName"));
                editor_vehicleDetails.putString("d1", snapshot.getString("VehicleDescription"));
                editor_vehicleDetails.putString("l1", snapshot.getString("Link"));
                editor_vehicleDetails.apply();
            }
        });

        db.collection("vehicle_details").document("Vehicle2").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                editor_vehicleDetails.putString("n2", snapshot.getString("VehicleName"));
                editor_vehicleDetails.putString("d2", snapshot.getString("VehicleDescription"));
                editor_vehicleDetails.putString("l2", snapshot.getString("Link"));
                editor_vehicleDetails.apply();
            }
        });

        db.collection("vehicle_details").document("Vehicle3").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                editor_vehicleDetails.putString("n3", snapshot.getString("VehicleName"));
                editor_vehicleDetails.putString("d3", snapshot.getString("VehicleDescription"));
                editor_vehicleDetails.putString("l3", snapshot.getString("Link"));
                editor_vehicleDetails.apply();
            }
        });

        db.collection("vehicle_details").document("Vehicle4").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                editor_vehicleDetails.putString("n4", snapshot.getString("VehicleName"));
                editor_vehicleDetails.putString("d4", snapshot.getString("VehicleDescription"));
                editor_vehicleDetails.putString("l4", snapshot.getString("Link"));
                editor_vehicleDetails.apply();
            }
        });

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
            switch (item.getItemId()) {
                case R.id.first_frag:
                    selectedFragment = new first_frag();
                    break;

                case R.id.sec_frag:
                    _frag:
                    selectedFragment = new sec_frag();
                    break;

                case R.id.third_frag:
                    t_frag:
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