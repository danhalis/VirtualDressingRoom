package ca.qc.johnabbott.cs5a6.virtualdressroom.data.dbhandlers;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


import ca.qc.johnabbott.cs5a6.virtualdressroom.data.models.GalleryPhoto;
import ca.qc.johnabbott.cs5a6.virtualdressroom.sqlite.DatabaseException;
import ca.qc.johnabbott.cs5a6.virtualdressroom.sqlite.Table;


import java.text.ParseException;




public class GalleryPhotoDBHandler extends SQLiteOpenHelper {

    public static final String DATABASE_FILE_NAME = "222galleryPhotos.db";
    public static final int DATABASE_VERSION = 1;
    public enum State { NONE, BEFORE_CHOOSE,CHOOSED,BEFORE_EDIT, EDITED, BEFORE_CREATE, CREATED}

    private GalleryPhotoDBHandler.State state;
    private GalleryPhoto originalPhoto;
    private GalleryPhoto updatedPhoto;

    private Table<GalleryPhoto> galleryPhotoTable;

    public GalleryPhotoDBHandler(@Nullable Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        galleryPhotoTable = new GalleryPhotoTable(this);
    }
    public Table<GalleryPhoto> getGalleryPhotoTable() {
        return galleryPhotoTable;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            galleryPhotoTable.createTable(db);

        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        if( galleryPhotoTable.hasInitialData()) {
            try {
                galleryPhotoTable.initialize(db);
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        galleryPhotoTable.dropTable(db);
        onCreate(db);
    }
    public void setState(GalleryPhotoDBHandler.State state) {
        this.state = state;
    }
    public GalleryPhoto getOriginalPhoto() {
        return originalPhoto;
    }
    public void setOriginalPhoto(GalleryPhoto originalPhoto) {
        this.originalPhoto = originalPhoto;
    }


}


