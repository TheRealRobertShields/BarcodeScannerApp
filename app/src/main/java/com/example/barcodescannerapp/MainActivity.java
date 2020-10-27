package com.example.barcodescannerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    private final int CAMERA_REQUEST_CODE = 101;
    private TextView currentStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpPermissions();

        CodeScannerView scannerView = findViewById(R.id.scannerView);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                ToneGenerator tone = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
                tone.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 150);
                currentStatus = findViewById(R.id.currentStatus);
                currentStatus.setText("Saved to database!\nTap screen to scan again.");
                    currentStatus.setTextColor(Color.WHITE);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMM yyyy (EEE) h:mm a");
                Date date = new Date();
                ScanData scanData = new ScanData(1, result.getText(), simpleDateFormat.format(date));
                ShowData.scanDataList.add(scanData);
                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                boolean success = databaseHelper.addOne(scanData);
                }
            });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentStatus = findViewById(R.id.currentStatus);
                if (currentStatus.getText() != "Scanning") {
                    currentStatus.setText("Scanning");
                    currentStatus.setTextColor(Color.RED);
                }
                mCodeScanner.startPreview();
            }
        });
    }

    public void goToViewData(View v) {
        Intent intent = new Intent(this, ShowData.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    private void setUpPermissions() {
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest();
        }
    }

    private void makeRequest() {
        ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA }, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "You need camera permission to be able to use this app!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "Ready.", Toast.LENGTH_SHORT).show();
                }
        }
    }


}
