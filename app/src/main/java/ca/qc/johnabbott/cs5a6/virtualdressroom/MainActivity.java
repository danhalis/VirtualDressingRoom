package ca.qc.johnabbott.cs5a6.virtualdressroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;

import java.util.List;

import ca.qc.johnabbott.cs5a6.virtualdressroom.data.dbhandlers.ApplicationDbHandler;
import ca.qc.johnabbott.cs5a6.virtualdressroom.data.models.GalleryPhoto;
import ca.qc.johnabbott.cs5a6.virtualdressroom.data.dbhandlers.GalleryPhotoDBHandler;
import ca.qc.johnabbott.cs5a6.virtualdressroom.ui.editor.CropPhotoFragment;
import ca.qc.johnabbott.cs5a6.virtualdressroom.ui.viewmodels.CropPhotoViewModel;

public class MainActivity extends AppCompatActivity {

    private CropPhotoViewModel cropPhotoViewModel;
    private CropPhotoFragment cropPhotoFragment;


    NavController navController;
    private ApplicationDbHandler applicationDbHandler;
    private GalleryPhotoDBHandler galleryPhotoDBHandler;
    private List<GalleryPhoto> data;

    public MainActivity() {
        applicationDbHandler = getApplicationDbHandler();
        galleryPhotoDBHandler = getGalleryPhotoDBHandler();

        cropPhotoViewModel = new CropPhotoViewModel();
    }

    public ApplicationDbHandler getApplicationDbHandler() {
        if (applicationDbHandler == null) {
            applicationDbHandler = new ApplicationDbHandler(this);
        }

        return applicationDbHandler;
    }

    public GalleryPhotoDBHandler getGalleryPhotoDBHandler() {
        if (galleryPhotoDBHandler == null) {
            galleryPhotoDBHandler = new GalleryPhotoDBHandler(this);
        }

        return galleryPhotoDBHandler;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
    }

    public NavController getNavController()
    {
        return navController;
    }

    public CropPhotoViewModel getCropPhotoViewModel() {
        return cropPhotoViewModel;
    }
}