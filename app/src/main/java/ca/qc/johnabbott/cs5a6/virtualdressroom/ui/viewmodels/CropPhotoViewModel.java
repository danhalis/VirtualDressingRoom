package ca.qc.johnabbott.cs5a6.virtualdressroom.ui.viewmodels;

import android.graphics.Bitmap;

public class CropPhotoViewModel extends ObservableModel<CropPhotoViewModel> {

    private Bitmap currentBitmap;


    public Bitmap getCurrentBitmap() {
        return currentBitmap;
    }

    public void setCurrentBitmap(Bitmap currentBitmap) {
        this.currentBitmap = currentBitmap;
    }

    @Override
    protected CropPhotoViewModel get() {
        return this;
    }
}
