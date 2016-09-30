package com.hayahytes.contentprovidertutorial.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class GmsaContract {
    public static final String CONTENT_AUTHORITY = "com.hayahytes.contentprovidertutorial";

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //define the content types
    public static final String PATH_STUDENT = "student";
    public static final String PATH_COMMITTEE = "committee";

    public static final class StudentEntry implements BaseColumns {
        //Content uri
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_STUDENT).build();

        //Content types
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/"+CONTENT_URI+"/"+PATH_STUDENT;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/"+CONTENT_URI+"/"+PATH_STUDENT;

        //table name
        public static final String TABLE_NAME = "student";

        //columns
        public static final String COL_NAME = "name";
        public static final String COL_STUDENT_ID = "student_id";
        public static final String COL_EMAIL = "email";
        public static final String COL_GENDER = "gender";
        public static final String COL_PHONE_NUMBER = "phone_number";

        public static Uri buildStudentUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }
    }

    public static final class CommitteeEntry implements BaseColumns {
        //Content uri
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_COMMITTEE).build();

        //Content types
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/"+CONTENT_URI+"/"+PATH_COMMITTEE;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/"+CONTENT_URI+"/"+PATH_COMMITTEE;

        //Table name
        public static final String TABLE_NAME = "committee";

        //Columns
        public static final String COL_NAME = "name";
        public static final String COL_CHAIRMAN = "chairman";
        public static final String COL_DATE_FORMED = "date_formed";

        public static Uri buildCommitteeUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
