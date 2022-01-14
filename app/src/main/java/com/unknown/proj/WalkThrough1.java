package com.unknown.proj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.card.MaterialCardView;

public class WalkThrough1 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_through1);

        findViewById(R.id.nextScreen).setOnClickListener(v -> {
            Intent intent = new Intent(WalkThrough1.this, Walkthrough2.class);
            startActivity(intent);
        });
    }
}