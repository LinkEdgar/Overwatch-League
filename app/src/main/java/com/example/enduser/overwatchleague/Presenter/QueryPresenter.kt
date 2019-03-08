package com.example.enduser.overwatchleague.Presenter

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.enduser.overwatchleague.ApiData.ApiDataRetriever
import com.example.enduser.overwatchleague.POJOs.OverwatchTeam
import com.example.enduser.overwatchleague.Services.UpdateTeamService

/**
 * Created by EndUser on 2/16/2019.
 */

class QueryPresenter(var context:Context, var mView: QueryContract.View, var intent: Intent): QueryContract.Presenter, ApiDataRetriever.RepositoryCallback {

    private lateinit var mRepo: ApiDataRetriever
    private  var mCacheData: ArrayList<OverwatchTeam> = ArrayList()
    private lateinit var mTeamHashSet: HashSet<String>

    init {
        getDataFromIntent()
        startTeamLoad()
    }

    private fun getDataFromIntent() {
        mTeamHashSet = intent.getSerializableExtra("set") as HashSet<String>
    }

    override fun startTeamLoad() {
        mView.setProgressBar()
        mRepo = ApiDataRetriever(this)
        mRepo.requestApiData()
    }

    override fun onSubmitQuery(query: String) {
        if(query.toLowerCase() == "show all")
            mView.updateUi(mCacheData)
        else{
            for(x in mCacheData.iterator()){
                val teamNameLowerCase = x.teamName.toLowerCase()
                if(teamNameLowerCase.startsWith(query) || teamNameLowerCase.endsWith(query)){
                    mView.updateUi(x)
                }else
                    Log.e("Not a match", "")
            }
        }

        for(x in mCacheData.iterator()){
            val teamNameLowerCase = x.teamName.toLowerCase()
            if(teamNameLowerCase.startsWith(query) || teamNameLowerCase.endsWith(query)){
                mView.updateUi(x)
            }else
                Log.e("Not a match", "")
        }
    }

    override fun onDataRetrieved(data: ArrayList<OverwatchTeam>) {
        mCacheData.clear()
        mCacheData.addAll(data)
        checkDataForSubscribedContent(mCacheData)
        mView.updateUi(data)
        mView.setProgressBar()
    }

    private fun checkDataForSubscribedContent(mCacheData: ArrayList<OverwatchTeam>) {
        for(x in mCacheData){
            if(mTeamHashSet.contains(x.teamName)){
                x.isSubbed = true
            }
        }
    }

    override fun onSubCheckBoxClicked(position: Int, team: OverwatchTeam) {
        val loadDbIntent = Intent(context, UpdateTeamService::class.java)
        if(team.isSubbed){
            //add to db
            loadDbIntent.action = insert_db_action
            loadDbIntent.putExtra(parcelableTeam, team)
        }else{
            //delete from db
            team.isSubbed = false
            loadDbIntent.action = delete_db_entry
            val query = "'"+ team.teamName + "'"
            loadDbIntent.putExtra(delete_db_entry, query)
        }
        context.startService(loadDbIntent)
    }

    companion object {
        const val delete_db_entry = "delete"
        const val insert_db_action = "insert"
        const val parcelableTeam = "overwatch_team"
    }

}