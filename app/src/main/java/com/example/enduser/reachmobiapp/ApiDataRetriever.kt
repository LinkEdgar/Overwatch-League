package com.example.enduser.reachmobiapp

import android.util.Log
import okhttp3.*
import java.io.IOException

/**
 * Created by EndUser on 2/16/2019.
 */
class ApiDataRetriever: Callback{

    private var client = OkHttpClient()

    fun requestApiData(url: String){
        val request = Request.Builder()
                .url(url)
                .build()
        client.newCall(request).enqueue(this)
    }

    override fun onResponse(call: Call?, response: Response?) {
        Log.e("response", ""+ (response?.body() ?: "error null"))
    }

    override fun onFailure(call: Call?, e: IOException?) {

    }

    companion object {

    }
}