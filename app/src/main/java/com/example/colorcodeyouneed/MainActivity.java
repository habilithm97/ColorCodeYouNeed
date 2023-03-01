package com.example.colorcodeyouneed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.oss.licenses.OssLicensesActivity;
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;
import com.skydoves.colorpickerview.sliders.AlphaSlideBar;
import com.skydoves.colorpickerview.sliders.BrightnessSlideBar;

public class MainActivity extends AppCompatActivity {

    private TextView colorCodeTv;
    private View colorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        colorCodeTv = (TextView)findViewById(R.id.colorCodeTv);
        colorView = (View)findViewById(R.id.colorView);
        ColorPickerView colorPickerView = (ColorPickerView)findViewById(R.id.colorPickerView);
        colorPickerView.setColorListener(new ColorEnvelopeListener() {
            @Override
            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                colorCodeTv.setText(envelope.getHexCode());
                colorView.setBackgroundColor(envelope.getColor());
            }
        });

        BrightnessSlideBar brightnessSlideBar = (BrightnessSlideBar)findViewById(R.id.brightnessSlideBar);
        colorPickerView.attachBrightnessSlider(brightnessSlideBar);

        AlphaSlideBar alphaSlideBar = (AlphaSlideBar)findViewById(R.id.alphaSlideBar);
        colorPickerView.attachAlphaSlider(alphaSlideBar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu1:
                break;
            case R.id.menu2:
                startActivity(new Intent(getApplicationContext(), OssLicensesMenuActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}