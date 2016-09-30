package com.hayahytes.contentprovidertutorial;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hayahytes.contentprovidertutorial.data.GmsaContract;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int STUDENT_LOADER_ID = 1;
    private SimpleCursorAdapter studentAdapter;

    private LoaderManager.LoaderCallbacks<Cursor> mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            String[] columns = {GmsaContract.StudentEntry.COL_NAME};
            int[] views = {R.id.student_name};

            //Insert some dummy data into the database
            //populateDatabase(getContentResolver());

            //Cursor c = getContentResolver().query(GmsaContract.StudentEntry.CONTENT_URI, null, null, null, null);
            studentAdapter = new SimpleCursorAdapter(this, R.layout.student_row, null, columns, views, 0);

            ListView listView = (ListView) findViewById(android.R.id.list);
            listView.setAdapter(studentAdapter);

            mCallbacks = this;
            getSupportLoaderManager().initLoader(STUDENT_LOADER_ID, null, mCallbacks);
        }catch(Exception ex){
            TextView textView = (TextView) findViewById(android.R.id.empty);
            textView.setText(ex.toString());
            toast(ex.toString());
        }
    }

    private void toast(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
    }
    private static void populateDatabase(ContentResolver contentResolver){
        ContentValues values = new ContentValues();
        for (int i=0; i < 50; i++) {
            values.put(GmsaContract.StudentEntry.COL_NAME, "Sulaiman" + i);
            values.put(GmsaContract.StudentEntry.COL_EMAIL, "saaryeetey.cos20@gmail.com" + i);
            values.put(GmsaContract.StudentEntry.COL_GENDER, "male" + i);
            values.put(GmsaContract.StudentEntry.COL_PHONE_NUMBER, "050134617" + i);
            values.put(GmsaContract.StudentEntry.COL_STUDENT_ID, "20347186" + i);

            contentResolver.insert(GmsaContract.StudentEntry.CONTENT_URI, values);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, GmsaContract.StudentEntry.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()){
            case STUDENT_LOADER_ID:
                studentAdapter.swapCursor(data);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        studentAdapter.swapCursor(null);
    }
}