package com.example.mvp.data.local;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.widget.Toast;

import java.util.HashMap;

 public class UsersDBProvider extends ContentProvider {

    static final String  PROVIDER_NAME = "com.example.mvp.data.local.UserDBProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/cpcontacts";
    public static final Uri CONTENT_URL = Uri.parse(URL);
    //static final String  TAG = "DatabaseHelper";
    static final String TableName="people_table";
    public static final String COL1= "ID";
    public static final String  COL2="name";
    public static final String  COL3="gender";
    public static final String  COL4="location";
    public static final String  COL5="email";
    public static final String  COL6="phone";
    final static int uriCode = 1;

    private static HashMap<String,String> values;
    static final UriMatcher uriMatcher ;

    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "cpcontacts", uriCode);
    }

    private SQLiteDatabase sqlDB;
    static final String DATABASE_NAME ="Contacts";
    static final int DATABASE_VERSION =1;
    static final String CREATE_DB_TABLE= "CREATE TABLE " + TableName + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL2 + " TEXT, " +  COL3 + " TEXT, " +
            COL4 + " TEXT, " + COL5 + " TEXT, " + COL6 + " TEXT);";




    @Override
    public boolean onCreate() {
        DataBaseHelper dbhelper = new DataBaseHelper(getContext());
        sqlDB = dbhelper.getWritableDatabase();
        if (sqlDB!=null)
            return  true;
        else return false;

    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TableName);

        switch (uriMatcher.match(uri)){
            case uriCode:
                queryBuilder.setProjectionMap(values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI" + uri);

        }

        Cursor cursor = queryBuilder.query(sqlDB, projection, selection, selectionArgs, null, null, sortOrder );

        cursor.setNotificationUri(getContext().getContentResolver(),uri);

        return cursor;
    }


    @Override
    public String getType( Uri uri) {

        switch (uriMatcher.match(uri)){
            case uriCode:
                return "vnd.android.cursor.dir/cpcontacts";

            default:
                throw new IllegalArgumentException("Unsupported URI" + uri);

        }
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = sqlDB.insert(TableName, null, values);
        if (rowID>0){
            Uri _uri = ContentUris.withAppendedId(CONTENT_URL, rowID);
            getContext().getContentResolver().notifyChange(_uri,null);
            return _uri;
        }else{
            Toast.makeText(getContext(),"Row Insert Failed",Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    @Override
    public int delete( Uri uri, String selection,  String[] selectionArgs) {

        int rowsdeleted =0;
        switch (uriMatcher.match(uri)){
            case uriCode:
                rowsdeleted =sqlDB.delete (TableName, selection, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI" + uri);

        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsdeleted;
    }

    @Override
    public int update( Uri uri,  ContentValues values,  String selection, String[] selectionArgs) {
        int rowsupdated =0;
        switch (uriMatcher.match(uri)){
            case uriCode:
                rowsupdated =sqlDB.update (TableName, values, selection, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI" + uri);

        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsupdated;
    }





    private static class DataBaseHelper extends SQLiteOpenHelper {

        public DataBaseHelper( Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL( "DROP TABLE IF EXISTS " + TableName);
            onCreate(db);
        }
    }
}
