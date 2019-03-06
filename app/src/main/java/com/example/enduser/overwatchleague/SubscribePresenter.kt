package com.example.enduser.overwatchleague

import android.content.Context
import android.database.Cursor
import android.util.Log
import org.jetbrains.anko.doAsync

class SubscribePresenter(var context: Context, var mView: SubscribeContract.View): SubscribeContract.Presenter{
    override fun loadSubcribedContentFromDb() {
        doAsync {
            val projection = arrayOf<String>(
                    OverwatchDbContract.TeamEntry.COLUMN_NAME_TEAM_NAME,
                    OverwatchDbContract.TeamEntry.COLUMN_NAME_ICON,
                    OverwatchDbContract.TeamEntry.COLUMN_NAME_PRIMARY_COLOR,
                    OverwatchDbContract.TeamEntry.COLUMN_NAME_SECONDARY_COLOR,
                    OverwatchDbContract.TeamEntry.COLUMN_NAME_MATCH_WIN,
                    OverwatchDbContract.TeamEntry.COLUMN_NAME_MATCH_LOSS,
                    OverwatchDbContract.TeamEntry.COLUMN_NAME_MATCH_DRAW)
            val cursor = context.contentResolver.query(OverwatchDbContract.CONTENT_URI, projection, null, null, null)
            cursor.moveToFirst()
            while(cursor.moveToNext()){
                extractDataFromCursor(cursor)
            }
            mView.updateUi(mData)
        }
    }

    private var mData = ArrayList<OverwatchTeam>()
    private var mTeamHashSet = HashSet<String>()
    init {
        //loadSubscribedContentFromDB()
    }


    private fun extractDataFromCursor(cursor: Cursor){
        val name = cursor.getString(0)
        val logo = cursor.getString(1)
        val colorPrimary = cursor.getString(2)
        val colorSecondary = cursor.getString(3)
        val matchWins = cursor.getString(4)
        val matchLoss = cursor.getString(5)
        val matchDraw = cursor.getString(6)
        val team = OverwatchTeam(name,logo, colorPrimary, colorSecondary)
        team.matchWin = matchWins
        team.matchLoss = matchLoss
        team.matchDraw = matchDraw
        if(!mTeamHashSet.contains(name))
            mData.add(team)
        mTeamHashSet.add(name)
    }

}