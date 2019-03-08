package com.example.enduser.overwatchleague.Presenter

import com.example.enduser.overwatchleague.POJOs.OverwatchTeam

interface SubscribeContract{

    interface View{
        fun updateUi(data: ArrayList<OverwatchTeam>, set: HashSet<String>)
        /*
        if the user doesn't have any subscriptions it sets a
        textview's visibility for user experience
         */
        fun setSubscriberMessage(boolean: Boolean)
    }

    interface Presenter{
        fun loadSubscribedContentFromDb()
    }
}