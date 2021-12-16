package ca.qc.johnabbott.cs5a6.virtualdressroom.data.dbhandlers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import ca.qc.johnabbott.cs5a6.virtualdressroom.data.models.GalleryPhoto;
import ca.qc.johnabbott.cs5a6.virtualdressroom.sqlite.Table;





import ca.qc.johnabbott.cs5a6.virtualdressroom.sqlite.Column;



public class GalleryPhotoTable extends Table<GalleryPhoto> {

    //table name and colume
    public static final String TABLE_NAME = "GalleryPhoto";

    public static final String COLUMN_WEBFORMATURL = "webformatURL";




    /**
     * Create a database table
     *
     * @param dbh the handler that connects to the sqlite database.
     */
    public GalleryPhotoTable(SQLiteOpenHelper dbh) {
        super(dbh, TABLE_NAME);

        addColumn(new Column(COLUMN_WEBFORMATURL, Column.Type.TEXT));

    }

    @Override
    protected ContentValues toContentValues(GalleryPhoto element) {

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        values.put(COLUMN_WEBFORMATURL, element.getWebformatURL());

        return values;
    }

    @Override
    protected GalleryPhoto fromCursor(Cursor cursor) {

        GalleryPhoto galleryPhoto = new GalleryPhoto()
                .setId(cursor.getInt(0))

                .setWebformatURL(cursor.getString(1));

        return galleryPhoto;
    }

    @Override
    public boolean hasInitialData() {

        return true;
    }

    @Override
    public void initialize(SQLiteDatabase database){


        database.insert("GalleryPhoto",null,toContentValues(new GalleryPhoto()

                .setWebformatURL("https://images.costco-static.com/ImageDelivery/imageService?profileId=12026539&itemId=100801680-894&recipeName=680&swatchId=grey&viewId=1")));
        database.insert("GalleryPhoto",null,toContentValues(new GalleryPhoto()

                .setWebformatURL("https://images.costco-static.com/ImageDelivery/imageService?profileId=12026539&itemId=100779328-894&recipeName=680&swatchId=black&viewId=1")));


        database.insert("GalleryPhoto",null,toContentValues(new GalleryPhoto()

                .setWebformatURL("https://images.costco-static.com/ImageDelivery/imageService?profileId=12026539&itemId=100463324-894&recipeName=680&viewId=1")));
        database.insert("GalleryPhoto",null,toContentValues(new GalleryPhoto()

                .setWebformatURL("https://images.costco-static.com/ImageDelivery/imageService?profileId=12026539&itemId=100800228-894&recipeName=680&viewId=1")));
        database.insert("GalleryPhoto",null,toContentValues(new GalleryPhoto()

                .setWebformatURL("https://images.costco-static.com/ImageDelivery/imageService?profileId=12026539&itemId=100758749-894&recipeName=680&swatchId=pink&viewId=1")));
        database.insert("GalleryPhoto",null,toContentValues(new GalleryPhoto()

                .setWebformatURL("https://richmedia.ca-richimage.com/ImageDelivery/imageService?profileId=12026539&id=2650712&recipeId=496")));
        database.insert("GalleryPhoto",null,toContentValues(new GalleryPhoto()

                .setWebformatURL("https://richmedia.ca-richimage.com/ImageDelivery/imageService?profileId=12026539&id=2650707&recipeId=496")));
        database.insert("GalleryPhoto",null,toContentValues(new GalleryPhoto()

                .setWebformatURL("https://images.costco-static.com/ImageDelivery/imageService?profileId=12026539&imageId=100785096-894__1&recipeName=350")));
        database.insert("GalleryPhoto",null,toContentValues(new GalleryPhoto()

                .setWebformatURL("https://richmedia.ca-richimage.com/ImageDelivery/imageService?profileId=12026539&id=2606649&recipeId=496")));
        database.insert("GalleryPhoto",null,toContentValues(new GalleryPhoto()

                .setWebformatURL("https://images.costco-static.com/ImageDelivery/imageService?profileId=12026539&itemId=100729489-894&recipeName=680&swatchId=black&viewId=1")));
        database.insert("GalleryPhoto",null,toContentValues(new GalleryPhoto()

                .setWebformatURL("https://images.costco-static.com/ImageDelivery/imageService?profileId=12026539&itemId=100776377-894&recipeName=680&swatchId=black&viewId=1")));
        database.insert("GalleryPhoto",null,toContentValues(new GalleryPhoto()

                .setWebformatURL("https://richmedia.ca-richimage.com/ImageDelivery/imageService?profileId=12026539&id=1673030&recipeId=496")));
        database.insert("GalleryPhoto",null,toContentValues(new GalleryPhoto()

                .setWebformatURL("https://richmedia.ca-richimage.com/ImageDelivery/imageService?profileId=12026539&id=2709347&recipeId=496")));
    }

}