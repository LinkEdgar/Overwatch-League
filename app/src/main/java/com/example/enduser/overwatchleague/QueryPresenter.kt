package com.example.enduser.overwatchleague

import android.content.Context
import android.support.annotation.MainThread
import android.util.Log

/**
 * Created by EndUser on 2/16/2019.
 */

class QueryPresenter(var context:Context, var mView: QueryContract.View): QueryContract.Presenter, ApiDataRetriever.RepositoryCallback {

    private lateinit var mRepo: ApiDataRetriever
    private  var mCacheData: ArrayList<OverwatchTeam> = ArrayList()

    init {
        startTeamLoad()
    }

    override fun startTeamLoad() {
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
        mView.updateUi(data)
    }

}