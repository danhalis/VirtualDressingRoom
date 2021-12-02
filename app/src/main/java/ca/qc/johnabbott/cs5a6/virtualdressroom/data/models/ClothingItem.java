package ca.qc.johnabbott.cs5a6.virtualdressroom.data.models;

import android.media.Image;

public class ClothingItem
{
    private ClothingType type;
    private ClothingStatus status;
    private byte[] image;
    private Long id;


    public ClothingItem(int id, ClothingType type, byte[] image)
    {
        this.setId((long) id);
        this.setType(type);
        this.setStatus(ClothingStatus.AVAILABLE);
        this.setImage(image);
    }

    public Long getId()
    {
        return this.id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public void setImage(byte[] image)
    {
        this.image = image;
    }

    public byte[] getImage()
    {
        return this.image;
    }

    public ClothingStatus getStatus()
    {
        return this.status;
    }

    public void setStatus(ClothingStatus status)
    {
        this.status = status;
    }

    public ClothingType getType()
    {
        return this.type;
    }

    public void setType(ClothingType type)
    {
        this.type = type;
    }

}
