package com.example.enduser.overwatchleague

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mQueryAdapter: QueryAdapter
    private lateinit var mLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUi()
    }

    override fun onResume() {
        super.onResume()
        testQuery()
    }

    private fun initUi(){
        switchButton.setOnClickListener{
            val switch = Intent(this, QueryActivity::class.java)
            startActivity(switch)
        }
    }


    //TODO delete this test
    private fun testQuery(){
        val PROJECTION = arrayOf<String>(OverwatchDbContract.TeamEntry.COLUMN_NAME_TEAM_NAME, OverwatchDbContract.TeamEntry.COLUMN_NAME_ICON )

        val cursor =contentResolver.query(OverwatchDbContract.CONTENT_URI, PROJECTION, null, null, null)


        if(cursor != null){
            cursor.moveToFirst()
            if(cursor.moveToFirst())
                Log.e("first", cursor.getString(0)+"  ,  "+ cursor.getString(1))
            else
                Log.e("DB", "null")
        }
    }

}
