package com.autoload.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class WalkThrough3 extends AppCompatActivity {

    SwipeListener swipeListner;
    ConstraintLayout constraintLayout;

    SharedPreferences AppData;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_through3);

        AppData = getSharedPreferences("AppData", MODE_PRIVATE);
        editor = AppData.edit();

        constraintLayout = findViewById(R.id.constraint_layout);
        swipeListner = new SwipeListener(constraintLayout);

        editor.putBoolean("isOpened", true);
        editor.apply();

        findViewById(R.id.nextScreen).setOnClickListener(v -> {
            Intent intent = new Intent(WalkThrough3.this, HomePage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            //overridePendingTransition(R.xml.fade_in, R.xml.fade_out);
        });
    }

    private class SwipeListener implements View.OnTouchListener {

        GestureDetector gestureDetector;

        SwipeListener(View view){
            int threshold = 100;
            int velocity_threshold = 100;

            GestureDetector.SimpleOnGestureListener listener =
                    new GestureDetector.SimpleOnGestureListener(){
                        @Override
                        public boolean onDown(MotionEvent e) {
                            return true;
                        }

                        @Override
                        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                            float xDiff = e2.getX() - e1.getX();
                            float yDiff = e2.getY()  - e1.getY();
                            try {
                                if ( Math.abs(xDiff) > Math.abs(yDiff)){

                                    if ( Math.abs(xDiff) > threshold && Math.abs(velocityX) > velocity_threshold ){
                                        if ( xDiff > 0 ){
                                            // Right Swipe
                                            Intent intent = new Intent(WalkThrough3.this, Walkthrough2.class);
                                            startActivity(intent);
                                            overridePendingTransition(R.xml.fade_in, R.xml.fade_out);
                                        }else{
                                            // Left Swipe
                                            Intent intent = new Intent(WalkThrough3.this, HomePage.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                            startActivity(intent);
                                            //overridePendingTransition(R.xml.fade_in, R.xml.fade_out);
                                        }
                                        return true;
                                    }
                                }else{
                                    if ( Math.abs(yDiff) > threshold && Math.abs(velocityY) > velocity_threshold ){
                                        if ( yDiff > 0 ){
                                            // Down Swipe
                                        }else{
                                            // Up Swipe
                                        }
                                        return true;
                                    }
                                }
                            }catch (Exception e) { e.printStackTrace(); }
                            return false;
                        }
                    };
            gestureDetector = new GestureDetector(listener);
            view.setOnTouchListener(this);
        }
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return gestureDetector.onTouchEvent(motionEvent);
        }
    }

}