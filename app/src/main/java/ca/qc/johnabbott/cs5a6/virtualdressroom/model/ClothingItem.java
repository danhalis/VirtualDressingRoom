package ca.qc.johnabbott.cs5a6.virtualdressroom.model;

import android.media.Image;

public class ClothingItem
{
    private ClothingType type;
    private ClothingStatus status;
    private Image image;
    private Long id;


    public ClothingItem(int id, ClothingType type)
    {
        this.setId((long) id);
        this.setType(type);
        this.setStatus(ClothingStatus.AVAILABLE);
    }







    public Long getId()
    {
        return this.id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Image getImage()
    {
        return this.image;
    }

    public void setImage(Image image)
    {
        this.image = image;
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
