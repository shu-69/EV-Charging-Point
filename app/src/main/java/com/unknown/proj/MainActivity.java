package com.unknown.proj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    boolean isPreInstalled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new nextScreenTimer().start();
    }

    private void goWalkThrough(){
        Intent intent = new Intent(MainActivity.this, WalkThrough1.class);

        startActivity(intent);
    }

    class nextScreenTimer extends Thread{
        public void run(){
            try {
                Thread.sleep(4000);

                if(isPreInstalled){

                }else{
                    goWalkThrough();
                }

            }catch (Exception mus){ }
        }
    }

}