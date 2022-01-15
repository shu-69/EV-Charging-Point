package com.unknown.proj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    boolean isPreInstalled = false;

    SharedPreferences AppData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppData = getSharedPreferences("AppData", MODE_PRIVATE);

        isPreInstalled  = AppData.getBoolean("isOpened", false);

        new nextScreenTimer().start();
    }

    private void goWalkThrough(){
        Intent intent = new Intent(MainActivity.this, WalkThrough1.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    private void goHomeScreen(){
        Intent intent = new Intent(MainActivity.this, HomePage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    class nextScreenTimer extends Thread{
        public void run(){
            try {
                Thread.sleep(4000);

                if(isPreInstalled){
                    goHomeScreen();
                }else{
                    goWalkThrough();
                }

                Thread.sleep(1000);
                finish();

            }catch (Exception mus){ goHomeScreen(); finish(); }

        }
    }

}