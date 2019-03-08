package com.example.enduser.overwatchleague

import android.app.IntentService
import android.content.ContentValues
import android.content.Intent


class UpdateTeamService: IntentService("UpdateDb"), DetailDataRetriever.OnResponseCallback{

    override fun onHandleIntent(intent: Intent) {

        if(intent.action == insert_team_action){
            addTeamToDb(intent)
        }else if(intent.action == delete_team_action){
            deleteTeamFromDb(intent)
        }
    }

    private fun createContentValues(team: OverwatchTeam): ContentValues{
        val contentValues = ContentValues()
        contentValues.put(OverwatchDbContract.TeamEntry.COLUMN_NAME_TEAM_NAME, team.teamName)
        contentValues.put(OverwatchDbContract.TeamEntry.COLUMN_NAME_ICON, team.teamIcon)
        contentValues.put(OverwatchDbContract.TeamEntry.COLUMN_NAME_PRIMARY_COLOR, team.teamPrimaryColor)
        contentValues.put(OverwatchDbContract.TeamEntry.COLUMN_NAME_SECONDARY_COLOR, team.teamSecondaryColor)
        contentValues.put(OverwatchDbContract.TeamEntry.COLUMN_NAME_ENTRY_ID, team.teamId)
        contentValues.put(OverwatchDbContract.TeamEntry.COLUMN_NAME_GAME_LOSS, team.gameLoss)
        contentValues.put(OverwatchDbContract.TeamEntry.COLUMN_NAME_GAME_TIE, team.gameTie)
        contentValues.put(OverwatchDbContract.TeamEntry.COLUMN_NAME_GAME_WIN, team.gameWin)
        contentValues.put(OverwatchDbContract.TeamEntry.COLUMN_NAME_MATCH_DRAW, team.matchDraw)
        contentValues.put(OverwatchDbContract.TeamEntry.COLUMN_NAME_MATCH_LOSS, team.matchLoss)
        contentValues.put(OverwatchDbContract.TeamEntry.COLUMN_NAME_MATCH_WIN, team.matchWin)
        contentValues.put(OverwatchDbContract.TeamEntry.COLUMN_NAME_FACEBOOK, team.accountFacebook)
        contentValues.put(OverwatchDbContract.TeamEntry.COLUMN_NAME_INSTAGRAM, team.accountInstagram)
        return contentValues


    }

    private fun addTeamToDb(intent: Intent){
        val team = intent.getParcelableExtra<OverwatchTeam>("overwatch_team")
        val detailDataRetriever = DetailDataRetriever(this,team) //gets additional team data
        detailDataRetriever.retrieveDetailData(team.teamId.toString())
    }

    companion object {
        const val insert_team_action = "insert"
        const val delete_team_action = "delete"
    }

    override fun onDataLoad(team: OverwatchTeam) {
        val contentValues = createContentValues(team)
        contentResolver.insert(OverwatchDbContract.CONTENT_URI, contentValues)
    }

    private fun deleteTeamFromDb(intent: Intent){
        val query = intent.getStringExtra("delete")
        contentResolver.delete(
                OverwatchDbContract.CONTENT_URI.buildUpon()
                        .appendPath(query)
                        .build(), null ,null )
    }

}
