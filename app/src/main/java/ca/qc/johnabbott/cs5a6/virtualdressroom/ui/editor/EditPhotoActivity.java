package ca.qc.johnabbott.cs5a6.virtualdressroom.ui.editor;

import ca.qc.johnabbott.cs5a6.virtualdressroom.R;
import ca.qc.johnabbott.cs5a6.virtualdressroom.ui.views.CropView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EditPhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CropView(EditPhotoActivity.this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(new CropView(EditPhotoActivity.this));
    }
}