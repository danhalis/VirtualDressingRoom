package ca.qc.johnabbott.cs5a6.virtualdressroom.data.models;

import android.content.Context;
import ca.qc.johnabbott.cs5a6.virtualdressroom.sqlite.Identifiable;
public class GalleryPhoto implements Identifiable<Long> {
    private String webformatURL;
    private long id;


    public GalleryPhoto(String webformatURL, long id) {
        this.webformatURL = webformatURL;
        this.id = id;

    }

    public GalleryPhoto() {

    }

    public GalleryPhoto(Context context) {
    }

    public String getWebformatURL() {
        return webformatURL;
    }

    public GalleryPhoto setWebformatURL(String webformatURL) {
        this.webformatURL = webformatURL;
        return this;
    }
  public Long getId() {
        return id;
    }

    @Override
    public Identifiable<Long> setId(Long id) {
        return this;
    }
    public GalleryPhoto setId(int id) {
        this.id = id;
        return this;
    }


}
