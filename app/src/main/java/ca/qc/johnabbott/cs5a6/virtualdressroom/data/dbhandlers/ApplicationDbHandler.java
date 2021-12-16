package ca.qc.johnabbott.cs5a6.virtualdressroom.data.dbhandlers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ca.qc.johnabbott.cs5a6.virtualdressroom.sqlite.DatabaseException;

public class ApplicationDbHandler extends SQLiteOpenHelper {

    public static final String DATABASE_FILE_NAME = "virtual-dressing-room.db";
    public static final int DATABASE_VERSION = 1;

    private final HeadPhotoTable headPhotoTable;
    private final UpperBodyOutfitPhotoTable upperBodyOutfitPhotoTable;
    private final LowerBodyOutfitPhotoTable lowerBodyOutfitPhotoTable;

    public ApplicationDbHandler(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        headPhotoTable = new HeadPhotoTable(this);
        upperBodyOutfitPhotoTable = new UpperBodyOutfitPhotoTable(this);
        lowerBodyOutfitPhotoTable = new LowerBodyOutfitPhotoTable(this);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            headPhotoTable.createTable(db);
            upperBodyOutfitPhotoTable.createTable(db);
            lowerBodyOutfitPhotoTable.createTable(db);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public HeadPhotoTable getHeadPhotoTable() {
        return headPhotoTable;
    }

    public UpperBodyOutfitPhotoTable getUpperBodyOutfitPhotoTable() {
        return upperBodyOutfitPhotoTable;
    }

    public LowerBodyOutfitPhotoTable getLowerBodyOutfitPhotoTable() {
        return lowerBodyOutfitPhotoTable;
    }
}
