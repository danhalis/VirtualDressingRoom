package ca.qc.johnabbott.cs5a6.virtualdressroom.ui.viewmodels;

import android.graphics.Bitmap;

import ca.qc.johnabbott.cs5a6.virtualdressroom.data.models.ClothingType;

public class CropPhotoViewModel extends ObservableModel<CropPhotoViewModel> {

    private Bitmap currentBitmap;

    private Long id;

    private ClothingType clothingType;

    private boolean isHead;

    public Bitmap getCurrentBitmap() {
        return currentBitmap;
    }

    public void setCurrentBitmap(Bitmap currentBitmap) {
        this.currentBitmap = currentBitmap;
    }

    public Long getId()
    {
        if (id == null) return -1L;
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

    public boolean getIsHead()
    {
        return isHead;
    }

    public void setIsHead(boolean isItAHead)
    {
        isHead = isItAHead;
    }

    public void reset() {
        id = null;
        clothingType = null;
        currentBitmap = null;
        isHead = false;
    }

    @Override
    protected CropPhotoViewModel get() {
        return this;
    }
}
