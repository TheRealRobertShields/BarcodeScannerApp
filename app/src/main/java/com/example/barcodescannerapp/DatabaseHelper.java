package com.example.barcodescannerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String SCANS_TABLE = "SCANS_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_SCAN_DATE = "SCAN_DATE";
    public static final String COLUMN_SCAN_DATA = "SCAN_DATA";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "scans.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + SCANS_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_SCAN_DATE + " TEXT, " + COLUMN_SCAN_DATA + " TEXT)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(ScanData scanData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_SCAN_DATE, scanData.getDateOfScan());
        cv.put(COLUMN_SCAN_DATA, scanData.getData());

        long insert = db.insert(SCANS_TABLE, null, cv);
        if (insert == 1) {
            return false;
        }
        else {
            return true;
        }
    }

    public List<ScanData> getAllScanData() {
        List<ScanData> returnList = new ArrayList<>();

        String query = "SELECT * FROM " + SCANS_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int scanDataID = cursor.getInt(0);
                String scanDate = cursor.getString(1);
                String scanData = cursor.getString(2);

                ScanData newScanData = new ScanData(scanDataID, scanData, scanDate);
                returnList.add(newScanData);
            } while (cursor.moveToNext());
        }
        else {
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public boolean deleteOne(ScanData scanData) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + SCANS_TABLE + " WHERE " + COLUMN_ID + " = " + scanData.getId();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            return true;
        }
        else {
            return false;
        }
    }
}
