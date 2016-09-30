package com.hayahytes.contentprovidertutorial.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

public class GmsaProvider extends ContentProvider{
    //Integer values that match unto specific Uris
    private static final int STUDENT = 100;
    private static final int STUDENT_ID = 101;
    private static final int COMMITTEE = 200;
    private static final int COMMITTEE_ID = 201;

    //Hold a reference to the database helper class
    private static GmsaDBHelper mGmsaDBHelper;

    //Use the UriMatcher class to map Uris to integer values hence we can put our
    // queries in switch statements
    private static final UriMatcher sUriMatcher;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        sUriMatcher.addURI(GmsaContract.CONTENT_AUTHORITY, GmsaContract.PATH_STUDENT, STUDENT);
        sUriMatcher.addURI(GmsaContract.CONTENT_AUTHORITY, GmsaContract.PATH_STUDENT + "/#", STUDENT_ID);
        sUriMatcher.addURI(GmsaContract.CONTENT_AUTHORITY, GmsaContract.PATH_COMMITTEE, COMMITTEE);
        sUriMatcher.addURI(GmsaContract.CONTENT_AUTHORITY, GmsaContract.PATH_COMMITTEE + "/#", COMMITTEE_ID);
    }

    @Override
    public boolean onCreate() {
        mGmsaDBHelper = new GmsaDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        //Get a reference to the database (Readable)
        final SQLiteDatabase db = mGmsaDBHelper.getReadableDatabase();
        Cursor resultCursor;

        switch (sUriMatcher.match(uri)){
            case STUDENT:
                resultCursor = db.query(
                        GmsaContract.StudentEntry.TABLE_NAME,
                        projection,
                        selection,
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            case STUDENT_ID:
                long student_id = ContentUris.parseId(uri);
                resultCursor = db.query(
                        GmsaContract.StudentEntry.TABLE_NAME,
                        projection,
                        GmsaContract.StudentEntry._ID + " = ?",
                        new String[]{String.valueOf(student_id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            case COMMITTEE:
                resultCursor = db.query(
                        GmsaContract.CommitteeEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case COMMITTEE_ID:
                long committee_id = ContentUris.parseId(uri);
                resultCursor = db.query(
                        GmsaContract.CommitteeEntry.TABLE_NAME,
                        projection,
                        GmsaContract.CommitteeEntry._ID + " = ?",
                        new String[]{String.valueOf(committee_id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknow uri: " + uri);
        }

        resultCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return resultCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)){
            case STUDENT:
                return GmsaContract.StudentEntry.CONTENT_TYPE;
            case STUDENT_ID:
                return GmsaContract.StudentEntry.CONTENT_ITEM_TYPE;
            case  COMMITTEE:
                return GmsaContract.CommitteeEntry.CONTENT_TYPE;
            case COMMITTEE_ID:
                return GmsaContract.CommitteeEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown Uri: "+ uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mGmsaDBHelper.getWritableDatabase();
        Uri resultUri;
        long _id;

        switch (sUriMatcher.match(uri)) {
            case STUDENT:
                _id = db.insert(GmsaContract.StudentEntry.TABLE_NAME, null, values);
                if( _id >= 0){
                    resultUri = GmsaContract.StudentEntry.buildStudentUri(_id);
                }else {
                    throw new UnsupportedOperationException("Unknow uri: " + uri);
                }
                break;
            case COMMITTEE:
                _id = db.insert(GmsaContract.CommitteeEntry.TABLE_NAME, null, values
                );
                if(_id >= 0){
                    resultUri = GmsaContract.CommitteeEntry.buildCommitteeUri(_id);
                }else {
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return resultUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mGmsaDBHelper.getWritableDatabase();
        int rowsDeleted; //number of rows deleted

        switch (sUriMatcher.match(uri)){
            case STUDENT:
                rowsDeleted = db.delete(GmsaContract.StudentEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case COMMITTEE:
                rowsDeleted = db.delete(GmsaContract.CommitteeEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(selection != null || rowsDeleted != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mGmsaDBHelper.getWritableDatabase();
        int rowsUpdated;

        switch (sUriMatcher.match(uri)){
            case STUDENT:
                rowsUpdated = db.update(GmsaContract.StudentEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case COMMITTEE:
                rowsUpdated = db.update(GmsaContract.CommitteeEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
