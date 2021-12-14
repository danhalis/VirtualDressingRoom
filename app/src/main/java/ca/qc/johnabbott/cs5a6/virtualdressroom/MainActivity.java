package ca.qc.johnabbott.cs5a6.virtualdressroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;

import java.util.List;

import ca.qc.johnabbott.cs5a6.virtualdressroom.model.GalleryPhoto;
import ca.qc.johnabbott.cs5a6.virtualdressroom.model.GalleryPhotoDBHandler;
import ca.qc.johnabbott.cs5a6.virtualdressroom.ui.editor.CropPhotoFragment;

public class MainActivity extends AppCompatActivity {

    private CropPhotoFragment cropPhotoFragment;

    NavController navController;
    private GalleryPhotoDBHandler galleryPhotoDBHandler;
    private List<GalleryPhoto> data;
    public MainActivity() {
        galleryPhotoDBHandler = getGalleryPhotoDBHandler();
    }


    public GalleryPhotoDBHandler getGalleryPhotoDBHandler() {
        if(galleryPhotoDBHandler==null){
            galleryPhotoDBHandler = new GalleryPhotoDBHandler(this);
        }
        return galleryPhotoDBHandler;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        //Intent intent = new Intent(this, EditPhotoActivity.class);
        //startActivity(intent);
    }

    public NavController getNavController()
    {
        return navController;
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