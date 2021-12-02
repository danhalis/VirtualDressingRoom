package ca.qc.johnabbott.cs5a6.virtualdressroom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ca.qc.johnabbott.cs5a6.virtualdressroom.ui.editor.CropPhotoFragment;

public class MainActivity extends AppCompatActivity {

    private CropPhotoFragment cropPhotoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent intent = new Intent(this, EditPhotoActivity.class);
//        startActivity(intent);
    }

    public void setCropPhotoFragment(CropPhotoFragment cropPhotoFragment) {
        this.cropPhotoFragment = cropPhotoFragment;
    }

    public CropPhotoFragment getCropPhotoFragment() {
        return cropPhotoFragment;
    }
}