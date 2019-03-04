package com.example.enduser.overwatchleague

interface SubscribeContract{

    interface View{
        fun updateUi(data: ArrayList<OverwatchTeam>)
    }

    interface Presenter{
        fun loadSubcribedContentFromDb()
    }
}