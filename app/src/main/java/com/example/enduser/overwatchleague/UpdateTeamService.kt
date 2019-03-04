package com.example.enduser.overwatchleague

import android.app.IntentService
import android.content.ContentValues
import android.content.Intent
import android.util.Log


class UpdateTeamService: IntentService("UpdateDb"){

    override fun onHandleIntent(intent: Intent) {

        if(intent.action == insert_team_action){
            val team = intent.getParcelableExtra<OverwatchTeam>("overwatch_team")
            val contentValues =createContentValues(team)
            contentResolver.insert(OverwatchDbContract.CONTENT_URI, contentValues)
        }else if(intent.action == delete_team_action){
            val query = intent.getStringExtra("delete")
            contentResolver.delete(
                    OverwatchDbContract.CONTENT_URI.buildUpon()
                            .appendPath(query)
                            .build(), null ,null )
        }
    }

    private fun createContentValues(team: OverwatchTeam): ContentValues{
        val contentValues = ContentValues()
        contentValues.put(OverwatchDbContract.TeamEntry.COLUMN_NAME_TEAM_NAME, team.teamName)
        contentValues.put(OverwatchDbContract.TeamEntry.COLUMN_NAME_ICON, team.teamIcon)
        contentValues.put(OverwatchDbContract.TeamEntry.COLUMN_NAME_PRIMARY_COLOR, team.teamPrimaryColor)
        contentValues.put(OverwatchDbContract.TeamEntry.COLUMN_NAME_SECONDARY_COLOR, team.teamSecondaryColor)
        return contentValues


    }

    companion object {
        val insert_team_action = "insert"
        val delete_team_action = "delete"
    }

}
