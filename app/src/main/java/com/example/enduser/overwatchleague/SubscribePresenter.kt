package com.example.enduser.overwatchleague

import android.content.Context
import android.database.Cursor
import org.jetbrains.anko.doAsync

class SubscribePresenter(var context: Context, var mView: SubscribeContract.View){

    private var mData = ArrayList<OverwatchTeam>()
    private var mTeamHashSet = HashSet<String>()
    init {
        loadSubscribedContentFromDB()
    }



    private fun loadSubscribedContentFromDB(){
      doAsync {
          val projection = arrayOf<String>(OverwatchDbContract.TeamEntry.COLUMN_NAME_TEAM_NAME, OverwatchDbContract.TeamEntry.COLUMN_NAME_ICON )
          val cursor = context.contentResolver.query(OverwatchDbContract.CONTENT_URI, projection, null, null, null)
          cursor.moveToFirst()
          while(cursor.moveToNext()){
              extractDataFromCursor(cursor)
          }
          mView.updateUi(mData)
      }
    }

    private fun extractDataFromCursor(cursor: Cursor){
        val name = cursor.getString(0)
        val logo = cursor.getString(1)
        //val color = cursor.getString(2)
        val team = OverwatchTeam(name,logo)
        if(!mTeamHashSet.contains(name))
            mData.add(team)
        mTeamHashSet.add(name)
    }

}