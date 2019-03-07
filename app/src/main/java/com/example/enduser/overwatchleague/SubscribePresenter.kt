package com.example.enduser.overwatchleague

import android.content.Context
import android.database.Cursor
import android.util.Log
import org.jetbrains.anko.doAsync

class SubscribePresenter(var context: Context, var mView: SubscribeContract.View): SubscribeContract.Presenter{

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
        val gameWin = cursor.getString(7)
        val gameLoss = cursor.getString(8)
        val gameTie = cursor.getString(9)

        val team = OverwatchTeam(name,logo, colorPrimary, colorSecondary)
        team.matchWin = matchWins
        team.matchLoss = matchLoss
        team.matchDraw = matchDraw
        team.gameWin = gameWin
        team.gameLoss = gameLoss
        team.gameTie = gameTie
        Log.e("team", "--> $team")
        if(!mTeamHashSet.contains(name))
            mData.add(team)
        mTeamHashSet.add(name)
    }

    override fun loadSubscribedContentFromDb() {
        doAsync {
            val projection = arrayOf(
                    OverwatchDbContract.TeamEntry.COLUMN_NAME_TEAM_NAME,
                    OverwatchDbContract.TeamEntry.COLUMN_NAME_ICON,
                    OverwatchDbContract.TeamEntry.COLUMN_NAME_PRIMARY_COLOR,
                    OverwatchDbContract.TeamEntry.COLUMN_NAME_SECONDARY_COLOR,
                    OverwatchDbContract.TeamEntry.COLUMN_NAME_MATCH_WIN,
                    OverwatchDbContract.TeamEntry.COLUMN_NAME_MATCH_LOSS,
                    OverwatchDbContract.TeamEntry.COLUMN_NAME_MATCH_DRAW,
                    OverwatchDbContract.TeamEntry.COLUMN_NAME_GAME_WIN,
                    OverwatchDbContract.TeamEntry.COLUMN_NAME_GAME_LOSS,
                    OverwatchDbContract.TeamEntry.COLUMN_NAME_GAME_TIE)
            val cursor = context.contentResolver.query(OverwatchDbContract.CONTENT_URI, projection, null, null, null)
            cursor.moveToFirst()
            mData.clear() //clears any data from previous loads
            mTeamHashSet.clear() //clears hash of temas from previous loads
            while(cursor.moveToNext()){
                extractDataFromCursor(cursor)
            }
            if(mData.isEmpty()){
                mView.setSubscriberMessage(true)
            }else
                mView.setSubscriberMessage(false)
            mView.updateUi(mData, mTeamHashSet)
        }
    }

}