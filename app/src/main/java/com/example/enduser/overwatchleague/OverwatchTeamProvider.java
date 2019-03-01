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
    private final static int TEAM_QUERY = 1;
    static{
        sUriMatcher.addURI(OverwatchDbContract.CONTENT_AUTHORITY, OverwatchDbContract.TEAM_DB_PATH, TEAM_QUERY );
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
        int match = sUriMatcher.match(uri);
        switch (match){
            case TEAM_QUERY:
                cursor = database.query(OverwatchDbContract.TeamEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null,null, sortOrder);
                break;
              default: throw new IllegalArgumentException("Cannot Query Unknow Uri" + uri);
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
        SQLiteDatabase database = mHelper.getWritableDatabase();
        long newRowId = database.insert(OverwatchDbContract.TeamEntry.TABLE_NAME, null, contentValues);
        if(newRowId == -1){
            Log.e("Db insertion", "Failed to insert row for " + uri);
            return null;
        }
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
