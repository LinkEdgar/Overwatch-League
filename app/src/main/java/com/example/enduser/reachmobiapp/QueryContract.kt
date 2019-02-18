package com.example.enduser.reachmobiapp

/**
 * Created by EndUser on 2/16/2019.
 */

interface QueryContract{
    interface View{

    }

    interface Presenter{
        fun onSubmitQuery(query: String)
    }
}