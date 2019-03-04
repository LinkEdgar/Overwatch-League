package com.example.enduser.overwatchleague;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class OverwatchTeamProvider extends ContentProvider{

    private OverwatchDBHelper mHelper;
    private static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    public final static int OV_TEAM = 1;
    public final static int OV_TEAM_SPECIFIC = 2;
    static{
        sUriMatcher.addURI(OverwatchDbContract.CONTENT_AUTHORITY, OverwatchDbContract.TEAM_DB_PATH, OV_TEAM);
        sUriMatcher.addURI(OverwatchDbContract.CONTENT_AUTHORITY, OverwatchDbContract.TEAM_DB_PATH+ "/*", OV_TEAM_SPECIFIC);
    }

    @Override
    public boolean onCreate() {
        mHelper = new OverwatchDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = mHelper.getReadableDatabase();
        Cursor cursor = null;
        Log.e("uri to query", "--> " + uri);
        int match = sUriMatcher.match(uri);
        switch (match){
            case OV_TEAM:
                cursor = database.query(OverwatchDbContract.TeamEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null,null, sortOrder);
                break;
              default: Log.e("UnknownUri", "--> "+ uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case OV_TEAM:
                return insertTeam(uri, contentValues);
            default:
                Log.e("Insertion is not valid ","-->"+ uri);
        }
        return uri;
    }

    private Uri insertTeam(Uri uri, ContentValues contentValues){
        SQLiteDatabase database = mHelper.getWritableDatabase();
        long newRowId = database.insert(OverwatchDbContract.TeamEntry.TABLE_NAME, null, contentValues);
        if(newRowId == -1){
            Log.e("Db insertion", "Failed to insert row for " + uri);
            return null;
        }
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mHelper.getWritableDatabase();
        int count = 0;
        switch(sUriMatcher.match(uri)){
            case OV_TEAM_SPECIFIC:
                String name = uri.getLastPathSegment();
                Log.e("delete team", "--> "+name);
                count = database.delete(OverwatchDbContract.TeamEntry.TABLE_NAME,
                        OverwatchDbContract.TeamEntry.COLUMN_NAME_TEAM_NAME + " = " +name,
                        null);
                break;
            default:
                count = 0;
                Log.e("Unknown URI: ","-->"+ uri);
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
