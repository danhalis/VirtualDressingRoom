package ca.qc.johnabbott.cs5a6.virtualdressroom.data.dbhandlers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;

import androidx.appcompat.content.res.AppCompatResources;

import ca.qc.johnabbott.cs5a6.virtualdressroom.R;
import ca.qc.johnabbott.cs5a6.virtualdressroom.data.models.Photo;
import ca.qc.johnabbott.cs5a6.virtualdressroom.sqlite.Column;
import ca.qc.johnabbott.cs5a6.virtualdressroom.sqlite.DatabaseException;
import ca.qc.johnabbott.cs5a6.virtualdressroom.sqlite.Table;
import ca.qc.johnabbott.cs5a6.virtualdressroom.ui.helper.BitmapHelper;

public class UpperBodyOutfitPhotoTable extends Table<Photo> {

    public static final String TABLE_NAME = "upperBodyOutfitPhoto";
    public static final String COLUMN_BYTES = "bytes";

    public UpperBodyOutfitPhotoTable(SQLiteOpenHelper dbh) {
        super(dbh, TABLE_NAME);
        addColumn(new Column(COLUMN_BYTES, Column.Type.BLOB));
    }

    @Override
    public void initialize(SQLiteDatabase database) throws DatabaseException {

    }

    @Override
    protected ContentValues toContentValues(Photo element) throws DatabaseException {
        ContentValues values = new ContentValues();

        // save photo bytes
        values.put(COLUMN_BYTES, element.getBytes());

        return values;
    }

    @Override
    protected Photo fromCursor(Cursor cursor) throws DatabaseException {

        Photo photo = new Photo()
                .setBytes(cursor.getBlob(1));

        photo.setId(cursor.getLong(0));

        return photo;
    }
}
