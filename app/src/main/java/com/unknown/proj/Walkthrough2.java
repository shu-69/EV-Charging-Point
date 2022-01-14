package com.unknown.proj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Walkthrough2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough2);

        findViewById(R.id.nextScreen).setOnClickListener(v -> {
            Intent intent = new Intent(Walkthrough2.this, Walkthrough2.class);
            startActivity(intent);
        });

    }
}