package mcs.salazar.jesus.contentprovidersdemo.component;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;

/**
 * Created by jesussalazar on 5/8/18.
 */

public class CustomContentProvider extends ContentProvider {
    static final String PROVIDER_NAME =
            "mcs.salazar.jesus.contentprovidersdemo.component.CustomContentProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/consultants";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    public static final String _ID = "_id";
    public static final String NAME = "name";
    public static final String GRADE = "grade";

    private static HashMap<String, String> STUDENTS_PROJECTION_MAP;

    static final int CONSULTANT = 1;
    static final int CONSULTANT_ID = 2;

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "consultants", CONSULTANT);
        uriMatcher.addURI(PROVIDER_NAME, "consultants/#", CONSULTANT_ID);
    }

    private SQLiteDatabase db;
    static final String DATABASE_NAME = "Mcs";
    static final String CONSULTANT_TABLE_NAME = "consultants";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + CONSULTANT_TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " name TEXT NOT NULL, " +
                    " grade TEXT NOT NULL);";

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return (db == null) ? false : true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = db.insert(	CONSULTANT_TABLE_NAME, "", values);
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case CONSULTANT:
                count = db.delete(CONSULTANT_TABLE_NAME, selection, selectionArgs);
                break;

            case CONSULTANT_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete( CONSULTANT_TABLE_NAME, _ID +  " = " + id +
                                ((!TextUtils.isEmpty(selection)) ?
                                        " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values,
                      String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case CONSULTANT:
                count = db.update(CONSULTANT_TABLE_NAME, values, selection, selectionArgs);
                break;

            case CONSULTANT_ID:
                count = db.update(CONSULTANT_TABLE_NAME, values,
                        _ID + " = " + uri.getPathSegments().get(1) +
                                (!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public Cursor query(Uri uri, String[] projection,
                        String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(CONSULTANT_TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case CONSULTANT:
                qb.setProjectionMap(STUDENTS_PROJECTION_MAP);
                break;

            case CONSULTANT_ID:
                qb.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
                break;

            default:
        }

        if (sortOrder == null || sortOrder == ""){
            /**
             * By default sort on student names
             */
            sortOrder = NAME;
        }

        Cursor c = qb.query(db,	projection,	selection,
                selectionArgs,null, null, sortOrder);
        /**
         * register to watch a content URI for changes
         */
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public String getType(Uri uri) {
        return null;
        /*switch (uriMatcher.match(uri)){
            *//**
             * Get all student records
             *//*
            case CONSULTANT:
                return "vnd.android.cursor.dir/vnd.example.students";
            *//**
             * Get a particular student
             *//*
            case CONSULTANT_ID:
                return "vnd.android.cursor.item/vnd.example.students";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }*/
    }





    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " +  CONSULTANT_TABLE_NAME);
            onCreate(db);
        }
    }


}
