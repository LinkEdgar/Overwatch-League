package com.example.enduser.overwatchleague

/**
 * Created by EndUser on 2/16/2019.
 */

interface QueryContract{
    interface View{
        fun updateUi(data: ArrayList<OverwatchTeam>)
        fun updateUi(team: OverwatchTeam)
    }

    interface Presenter{
        fun onSubmitQuery(query: String)
        fun startTeamLoad()
        fun onSubCheckBoxClicked(position: Int, team: OverwatchTeam)
    }
}