package ca.qc.johnabbott.cs5a6.virtualdressroom.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import ca.qc.johnabbott.cs5a6.virtualdressroom.model.GalleryPhoto;
import ca.qc.johnabbott.cs5a6.virtualdressroom.sqlite.Table;

import java.sql.Date;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import ca.qc.johnabbott.cs5a6.virtualdressroom.sqlite.Column;
import ca.qc.johnabbott.cs5a6.virtualdressroom.sqlite.DatabaseException;
import ca.qc.johnabbott.cs5a6.virtualdressroom.sqlite.Table;

public class GalleryPhotoTable extends Table<GalleryPhoto> {

    //table name and colume
    public static final String TABLE_NAME = "GalleryPhoto";

    public static final String COLUMN_WEBFORMATURL = "webformatURL";

    ;


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
    protected ContentValues toContentValues(GalleryPhoto element) throws DatabaseException {

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        values.put(COLUMN_WEBFORMATURL, element.getWebformatURL());

        return values;
    }

    @Override
    protected GalleryPhoto fromCursor(Cursor cursor) throws DatabaseException {

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
    public void initialize(SQLiteDatabase database) throws ParseException, DatabaseException {


        database.insert("GalleryPhoto",null,toContentValues(new GalleryPhoto()

                .setWebformatURL("https://pixabay.com/get/g7e733b544d2eb9286fbe76a7162a7deee9815d0f0393d451eeb73d048c88a9e2f83bd929c5dfcf0671c984504e3ca8f3_640.jpg")
               ));
        database.insert("GalleryPhoto",null,toContentValues(new GalleryPhoto()

                .setWebformatURL("https://pixabay.com/get/ge797ce239bf3380845aa303ed66d0cd0fd0c9023e347efa0485c81e05fc00386ef2f2aa4bc3a2af4920b30dd51c709022041a4c637435c4ed331e87025ca1916_640.jpg")
                ));


        database.insert("GalleryPhoto",null,toContentValues(new GalleryPhoto()

                .setWebformatURL("https://pixabay.com/get/g62453719264d5edbd4d5c730506617164267961264737c6f4005a331f465ad7172ca4519b43f91c1e4b82458f71ba324_640.jpg")
                ));
        database.insert("GalleryPhoto",null,toContentValues(new GalleryPhoto()

                .setWebformatURL("https://images.costco-static.com/ImageDelivery/imageService?profileId=12026539&itemId=100800228-894&recipeName=680&viewId=1")
               ));
        database.insert("GalleryPhoto",null,toContentValues(new GalleryPhoto()

                .setWebformatURL("https://pixabay.com/get/g0537ff9e15ea77e4033737a147cf43a5a5a0eaa1d416a7a30480bc979d3ee2d7bd211243666a9522b5d1c6218b87aae602bf773be5c8ec607da208b962a9bbe7_640.jpg")
                ));
        database.insert("GalleryPhoto",null,toContentValues(new GalleryPhoto()

                .setWebformatURL("https://pixabay.com/get/g4976f7c4b2b170a391316340fbe71d5f9c2f59c6e37f3080e595e7abd8b6e369175306443138f3e58e0976c018dc9edfd209890d113a83152bef5ef15c2531d8_640.jpg")
              ));
/*
    }
    private String getUrl() {
        return "https://pixabay.com/api/?key=24637787-3891e90ceb2f5d5081dd40724&q=yellow+flowers&image_type=photo&pretty=true";
      //  return MessageFormat.format("https://pixabay.com/api/?key=24637787-3891e90ceb2f5d5081dd40724&q={0}.", keyWords);
    }

  //  private String[] keyWords = {"clothes", "man", "woman"};


}
/*
        final SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd'T'hhmmss'Z'");

        database.insert("task",null,toContentValues(new Task()
                .setUuid(UUID.fromString("eee94ca2-70ea-4114-8f8d-330c199bae50"))
                .setDescription("Recycling")
                .setStatus(Status.PENDING)
                .setPriority(Priority.LOW)
                .setEntry(fmt.parse("20210916T002338Z"))
                .setModified(fmt.parse("20210916T002501Z"))
                .setDue(fmt.parse("20210920T083000Z"))));
        database.insert("task",null,toContentValues(new Task()
                .setUuid(UUID.fromString("a557b5ea-a652-4719-86e8-a2bbf7ffddae"))
                .setDescription("Garbage")
                .setStatus(Status.PENDING)
                .setPriority(Priority.LOW)
                .setEntry(fmt.parse("20210916T002405Z"))
                .setModified(fmt.parse("20210916T002501Z"))
                .setDue(fmt.parse("20210920T083000Z"))));
        database.insert("task",null,toContentValues(new Task()
                .setUuid(UUID.fromString("dacf5b66-0386-43f4-b95a-3a838b7fee83"))
                .setDescription("Assignment 2 instructions")
                .setStatus(Status.PENDING)
                .setPriority(Priority.MEDIUM)
                .setEntry(fmt.parse("20210916T002542Z"))
                .setModified(fmt.parse("20210916T002801Z"))
                .setDue(fmt.parse("20210923T040000Z"))));
    }
*/
    }}