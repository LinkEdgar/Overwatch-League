package com.example.enduser.overwatchleague

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SubscribeContract.View {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mSubscribedAdapter: SubscribedAdapter
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mData: ArrayList<OverwatchTeam>

    private lateinit var mPresenter: SubscribePresenter
    private lateinit var mTeamHashSet: HashSet<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUi()
        loadFakeData()
        mPresenter = SubscribePresenter(this, this)
    }

    override fun onResume() {
        super.onResume()
        testQuery()
        mPresenter.loadSubcribedContentFromDb()
    }

    private fun initUi(){
        switchButton.setOnClickListener{
            val switch = Intent(this, QueryActivity::class.java)
            startActivity(switch)
        }
        mRecyclerView = rv_user_subs
        mData = ArrayList()
        mSubscribedAdapter = SubscribedAdapter(this,mData)
        mLayoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mSubscribedAdapter
        mRecyclerView.layoutManager = mLayoutManager
        mTeamHashSet = HashSet()
    }

    private fun loadFakeData(){
        mData.add(OverwatchTeam("Yeet"))
        mData.add(OverwatchTeam("Yeet 2"))
        mData.add(OverwatchTeam("Yeet 3"))
        mData.add(OverwatchTeam("Yeet 4"))
        mSubscribedAdapter.notifyDataSetChanged()
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

    override fun updateUi(data: ArrayList<OverwatchTeam>) {
        var mainHandler = Handler(Looper.getMainLooper())

        var myRunnable =
                Runnable {
                    mData.clear()
                    mData.addAll(data)
                    mSubscribedAdapter.notifyDataSetChanged()
                }
        mainHandler.post(myRunnable)
    }

}
