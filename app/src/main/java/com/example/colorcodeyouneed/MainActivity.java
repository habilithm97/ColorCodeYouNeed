package com.example.colorcodeyouneed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.oss.licenses.OssLicensesActivity;
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;
import com.skydoves.colorpickerview.sliders.AlphaSlideBar;
import com.skydoves.colorpickerview.sliders.BrightnessSlideBar;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText colorCodeEdt;
    private View colorView;

    private InputFilter inputFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$"); // 영어와 숫자만 허용
                if (!pattern.matcher(source).matches()) {
                    return "";
                }
                return null;
            }
        };

        initView();
    }

    private void initView() {
        colorCodeEdt = (EditText) findViewById(R.id.colorCodeEdt);
        colorCodeEdt.setFilters(new InputFilter[] {inputFilter, new InputFilter.LengthFilter(8)}); // 영어와 숫자만 허용하는 필터 적용(최대 길이 8자리 제한)
        colorCodeEdt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String colorCode = colorCodeEdt.getText().toString();
                createClipData(colorCode);
                return true;
            }
        });

        colorView = (View)findViewById(R.id.colorView);
        colorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String colorCode = colorCodeEdt.getText().toString();
                Intent intent = new Intent(getApplicationContext(), ColorActivity.class);
                intent.putExtra("colorCode", colorCode);
                startActivity(intent);
            }
        });

        ColorPickerView colorPickerView = (ColorPickerView)findViewById(R.id.colorPickerView);
        colorPickerView.setColorListener(new ColorEnvelopeListener() {
            @Override
            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                colorCodeEdt.setText(envelope.getHexCode());
                colorView.setBackgroundColor(envelope.getColor());
            }
        });

        BrightnessSlideBar brightnessSlideBar = (BrightnessSlideBar)findViewById(R.id.brightnessSlideBar);
        colorPickerView.attachBrightnessSlider(brightnessSlideBar);

        AlphaSlideBar alphaSlideBar = (AlphaSlideBar)findViewById(R.id.alphaSlideBar);
        colorPickerView.attachAlphaSlider(alphaSlideBar);
    }

    private void createClipData(String text) {
        // 텍스트 복사
        ClipboardManager clipboardManager = (ClipboardManager) getApplicationContext().getSystemService(getApplicationContext().CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text", text);
        clipboardManager.setPrimaryClip(clipData); // 복사한 텍스트를 클립보드에 배치
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
            case R.id.restart:
                String colorCode = colorCodeEdt.getText().toString();
                if(colorCodeEdt.getText().toString().length() == 8) { // 코드 길이가 8자리일 때만
                    colorView.setBackgroundColor(Color.parseColor("#" + colorCode));
                } 
        }
        return super.onOptionsItemSelected(item);
    }
}