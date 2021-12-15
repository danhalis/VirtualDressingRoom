package ca.qc.johnabbott.cs5a6.virtualdressroom.ui.viewmodels;

import android.graphics.Bitmap;

import ca.qc.johnabbott.cs5a6.virtualdressroom.data.models.ClothingType;

public class CropPhotoViewModel extends ObservableModel<CropPhotoViewModel> {

    private Bitmap currentBitmap;

    private Long id;

    private ClothingType clothingType;


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

    public ClothingType getClothingType()
    {
        return this.clothingType;
    }

    public void setClothingType(ClothingType type)
    {
        this.clothingType = type;
    }



    @Override
    protected CropPhotoViewModel get() {
        return this;
    }
}
