package com.example.enduser.overwatchleague.Views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.enduser.overwatchleague.*
import com.example.enduser.overwatchleague.POJOs.OverwatchTeam
import com.example.enduser.overwatchleague.Presenter.QueryContract
import com.example.enduser.overwatchleague.Presenter.QueryPresenter
import com.example.enduser.overwatchleague.Views.Adapters.QueryAdapter
import kotlinx.android.synthetic.main.activity_query.*

class QueryActivity : AppCompatActivity(), QueryContract.View, SearchView.OnQueryTextListener, QueryAdapter.TeamUpdateCallback {

    private lateinit var mRecyclerView:RecyclerView
    private lateinit var mQueryAdapter: QueryAdapter
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mData: ArrayList<OverwatchTeam>

    private lateinit var mSearchView: SearchView

    private lateinit var mPresenter: QueryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_query)

        initUi()
        mPresenter = QueryPresenter(this, this, intent)
    }

    private fun initUi(){
        mData = ArrayList()
        mRecyclerView = query_recycler
        mQueryAdapter = QueryAdapter(this, mData, this)
        mRecyclerView.adapter = mQueryAdapter
        mLayoutManager = LinearLayoutManager(this)
        mRecyclerView.layoutManager = mLayoutManager
        mSearchView = search_view
        mSearchView.setOnQueryTextListener(this)
    }

    override fun updateUi(data: ArrayList<OverwatchTeam>) {

        var mainHandler = Handler(Looper.getMainLooper())

        var myRunnable =
                Runnable {
                    mData.clear()
                    mData.addAll(data)
                    mQueryAdapter.notifyDataSetChanged()
                }
        mainHandler.post(myRunnable)
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        mPresenter.onSubmitQuery(query.toLowerCase().trim())
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText == ""){
            mPresenter.onClearSearch()
        }
        return true
    }

    override fun updateUi(team: OverwatchTeam) {
        mData.clear()
        mData.add(team)
        mQueryAdapter.notifyDataSetChanged()
    }

    override fun updateTeam(position: Int, team: OverwatchTeam) {
        mData.set(position, team)
        mQueryAdapter.notifyItemChanged(position)
        mPresenter.onSubCheckBoxClicked(position, team)
    }

    override fun setProgressBar() {
        var mainHandler = Handler(Looper.getMainLooper())

        var myRunnable =
                Runnable {
                    if(progressBar.visibility == View.GONE){
                        progressBar.visibility = View.VISIBLE
                    }else{
                        progressBar.visibility = View.GONE
                    }
                }
        mainHandler.post(myRunnable)
    }

}
