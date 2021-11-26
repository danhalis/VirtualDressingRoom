package ca.qc.johnabbott.cs5a6.cropimage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CropView(MainActivity.this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(new CropView(MainActivity.this));
    }
}