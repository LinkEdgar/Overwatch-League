package com.example.enduser.overwatchleague;

import android.net.Uri;
import android.provider.BaseColumns;

public class OverwatchDbContract {
    private OverwatchDbContract(){}

    public static final String CONTENT_AUTHORITY = "com.example.enduser.overwatchleague";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+ CONTENT_AUTHORITY);
    public static final String TEAM_DB_PATH = "overwatch_team_table";
    public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, TEAM_DB_PATH);

    //these variables should probably go somewhere else
    public static final String BROADCAST_ACTION = "com.example.enduser.overwatchleague.BROADCAST";
    public static final String DATA_STATUS = "com.example.enduser.overwatchleague.STATUS";


    public static class TeamEntry implements BaseColumns {
        public static final String TABLE_NAME = "team_table";
        public static final String COLUMN_NAME_ENTRY_ID = "entry_id";
        public static final String COLUMN_NAME_TEAM_NAME = "name";
        public static final String COLUMN_NAME_HOME_LOCATION = "homeLocation";
        public static final String COLUMN_NAME_INSTAGRAM = "instagram";
        public static final String COLUMN_NAME_FACEBOOK = "facebook";
        public static final String COLUMN_NAME_YOUTUBE = "youtube";
        public static final String COLUMN_NAME_ICON = "icon";
    }

}
