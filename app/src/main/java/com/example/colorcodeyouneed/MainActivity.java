package com.example.colorcodeyouneed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
        colorCodeTv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String colorCode = colorCodeTv.getText().toString();
                createClipData(colorCode);
                return true;
            }
        });

        colorView = (View)findViewById(R.id.colorView);
        colorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String colorCode = colorCodeTv.getText().toString();
                Intent intent = new Intent(getApplicationContext(), ColorActivity.class);
                intent.putExtra("colorCode", colorCode);
                startActivity(intent);
            }
        });

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

    private void createClipData(String text) {
        // 클립보드 복사
        ClipboardManager manager = (ClipboardManager) getApplicationContext().getSystemService(getApplicationContext().CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text", text);
        // 클립보드에 배치
        manager.setPrimaryClip(clipData);
        Toast.makeText(getApplicationContext(), "복사되었습니다. ", Toast.LENGTH_SHORT).show();
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