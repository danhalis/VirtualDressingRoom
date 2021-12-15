package ca.qc.johnabbott.cs5a6.virtualdressroom.ui.viewmodels;

import android.graphics.Bitmap;

public class CropPhotoViewModel extends ObservableModel<CropPhotoViewModel> {

    private Bitmap currentBitmap;

    private Long id;


    public Bitmap getCurrentBitmap() {
        return currentBitmap;
    }

    public void setCurrentBitmap(Bitmap currentBitmap) {
        this.currentBitmap = currentBitmap;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long theId)
    {
        id = theId;
    }

    @Override
    protected CropPhotoViewModel get() {
        return this;
    }
}
