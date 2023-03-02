package com.example.colorcodeyouneed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class ColorActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);

        intent = getIntent();

        initView();
    }

    private void initView() {
        String colorCode = intent.getStringExtra("colorCode");
        View mainView = (View)findViewById(R.id.mainView);
        mainView.setBackgroundColor(Color.parseColor("#" + colorCode));
    }
}