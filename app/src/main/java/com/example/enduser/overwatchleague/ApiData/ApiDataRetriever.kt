package com.example.enduser.overwatchleague.ApiData

import android.util.Log
import com.example.enduser.overwatchleague.POJOs.OverwatchTeam
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import org.json.JSONException


/**
 * Created by EndUser on 2/16/2019.
 */
class ApiDataRetriever(var mCallback: RepositoryCallback): Callback{

    private var client = OkHttpClient()
    private var mData: ArrayList<OverwatchTeam> = ArrayList()

    interface RepositoryCallback{
        fun onDataRetrieved(data: ArrayList<OverwatchTeam>)
    }

    fun requestApiData(){
        val request = Request.Builder()
                .url(overwatchTeams)
                .build()
        client.newCall(request).enqueue(this)
    }

    override fun onResponse(call: Call?, response: Response?) {
        val json = JSONObject(response?.body()?.string())

        val jArray = json.getJSONArray("competitors")

        for (i in 0 until jArray.length()) {
            try {
                extractTeamsFromJsonArray(jArray, i)
            } catch (any: JSONException) {
                Log.e("onResponse", any.printStackTrace().toString())
            }
        }
        mCallback.onDataRetrieved(mData)

    }

    private fun extractTeamsFromJsonArray(jArray: JSONArray, i: Int){
        val rootJson = jArray.getJSONObject(i)
        val childJson = rootJson.getJSONObject("competitor")
        val teamName = childJson.get("name").toString()
        val teamImage = childJson.get("logo").toString()
        val teamPrimaryColor = "#"+childJson.get("primaryColor").toString()
        val teamSecondaryColor = "#"+childJson.get("secondaryColor").toString()
        val teamId = childJson.get("id").toString()
        val overwatchTeam = OverwatchTeam(teamName, teamImage, teamPrimaryColor, teamSecondaryColor)
        overwatchTeam.teamId = teamId
        mData.add(overwatchTeam)
    }


    override fun onFailure(call: Call?, e: IOException?) {

    }

    companion object {
        const val overwatchTeams = "https://api.overwatchleague.com/teams/"
    }

    fun getData(): ArrayList<OverwatchTeam>{
        return mData
    }
}