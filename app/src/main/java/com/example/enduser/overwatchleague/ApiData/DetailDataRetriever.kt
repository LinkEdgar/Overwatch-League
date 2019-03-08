package com.example.enduser.overwatchleague.ApiData

import android.util.Log
import com.example.enduser.overwatchleague.POJOs.OverwatchTeam
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class DetailDataRetriever(var callback: OnResponseCallback, var team: OverwatchTeam) : Callback {

    private var client = OkHttpClient()

    interface OnResponseCallback {
        fun onDataLoad(team: OverwatchTeam)
    }

    fun retrieveDetailData(teamId: String) {
        val request = Request.Builder()
                .url(ApiDataRetriever.overwatchTeams + teamId)
                .build()
        client.newCall(request).enqueue(this)
    }


    override fun onResponse(call: Call, response: Response) {
        val json = JSONObject(response.body()?.string())

        try {
            extractRankings(json)
            extractAccounts(json)
        } catch (j: JSONException) {
            Log.e("response", "--> ${j.printStackTrace()}")
        } finally {
            callback.onDataLoad(team)
        }
    }

    override fun onFailure(call: Call, e: IOException) {

    }

    private fun extractRankings(json: JSONObject) {
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
    }

    private fun extractAccounts(json: JSONObject) {
        val accountsRoot = json.getJSONArray("accounts")
        for (x in 0..(accountsRoot.length() - 1)) {
            val accountChild = accountsRoot.getJSONObject(x)
            val accountType = accountChild.getString("accountType")
            setAccount(accountType, accountChild.getString("value"))
        }
    }

    private fun setAccount(accountType: String, value: String){
        when(accountType){
            facebook_account -> {team.accountFacebook = value}
            twitter_account -> {team.accountTwitter= value}
            youtube_account -> {team.accountYoutube = value}
            instagram_account -> {team.accountInstagram = value}
        }
    }

    companion object {
        const val facebook_account = "FACEBOOK"
        const val instagram_account = "INSTAGRAM"
        const val youtube_account = "YOUTUBE_CHANNEL"
        const val twitter_account = "TWITTER"
    }
}