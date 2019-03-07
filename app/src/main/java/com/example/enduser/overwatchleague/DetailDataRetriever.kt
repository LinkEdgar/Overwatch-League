package com.example.enduser.overwatchleague

import android.util.Log
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class DetailDataRetriever(var callback: OnResponseCallback, var team: OverwatchTeam) : Callback {

    private var client = OkHttpClient()

    interface OnResponseCallback{
        fun onDataLoad(team: OverwatchTeam)
    }

    fun retrieveDetailData(teamId: String){
        val request = Request.Builder()
                .url(ApiDataRetriever.overwatchTeams+teamId)
                .build()
        client.newCall(request).enqueue(this)
    }


    override fun onResponse(call: Call, response: Response) {
        val json = JSONObject(response.body()?.string())

        try {
            //error
            val rankings = json.getJSONObject("ranking")
            val matchWin = rankings.getString("matchWin")
            val matchLoss = rankings.getString("matchLoss")
            val matchDraw = rankings.getString("matchDraw")
            val gameWin = rankings.getString("gameWin")
            val gameLoss = rankings.getString("gameLoss")
            val gameTie = rankings.getString("gameTie")
            team.matchWin = matchWin
            team.matchDraw = matchDraw
            team.matchLoss = matchLoss
            team.gameTie = gameTie
            team.gameLoss = gameLoss
            team.gameWin = gameWin
        }catch (j : JSONException){
            Log.e("response","--> ${j.printStackTrace()}")
        }
        finally {
            callback.onDataLoad(team)
        }
    }

    override fun onFailure(call: Call, e: IOException) {

    }
}