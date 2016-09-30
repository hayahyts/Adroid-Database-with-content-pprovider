package com.hayahytes.contentprovidertutorial.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GmsaDBHelper extends SQLiteOpenHelper{
    //Database name
    private static final String DATABASE_NAME = "gmsa.db";
    //Database version
    private static final int DATABASE_VERSION = 1;


    public GmsaDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createStudentTable(db);
        createCommitteeTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //No need to upgrade the database now
    }

    private static void createStudentTable(SQLiteDatabase db){
        db.execSQL(
                "CREATE TABLE " + GmsaContract.StudentEntry.TABLE_NAME + "(" +
                        GmsaContract.StudentEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        GmsaContract.StudentEntry.COL_NAME + " TEXT NOT NULL, " +
                        GmsaContract.StudentEntry.COL_GENDER + " TEXT NOT NULL, " +
                        GmsaContract.StudentEntry.COL_EMAIL + " TEXT NOT NULL, " +
                        GmsaContract.StudentEntry.COL_PHONE_NUMBER + " TEXT NOT NULL, " +
                        GmsaContract.StudentEntry.COL_STUDENT_ID + " TEXT NOT NULL);"

        );
    }

    private static void createCommitteeTable(SQLiteDatabase db){
        db.execSQL(
                "CREATE TABLE " + GmsaContract.CommitteeEntry.TABLE_NAME + "(" +
                        GmsaContract.CommitteeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        GmsaContract.CommitteeEntry.COL_NAME + " TEXT UNIQUE NOT NULL, " +
                        GmsaContract.CommitteeEntry.COL_CHAIRMAN + " TEXT NOT NULL, " +
                        GmsaContract.CommitteeEntry.COL_DATE_FORMED + " TEXT NOT NULL);"
        );
    }
}
