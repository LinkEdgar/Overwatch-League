package com.example.enduser.overwatchleague

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class QueryAdapter(var context:Context, var data:ArrayList<OverwatchTeam>, var callBack: TeamUpdateCallback): RecyclerView.Adapter<QueryAdapter.QueryViewHolder>(){

    interface TeamUpdateCallback{
        fun updateTeam(position: Int, team: OverwatchTeam)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): QueryViewHolder {
        return QueryViewHolder(LayoutInflater.from(context).inflate(R.layout.query_container, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: QueryViewHolder, position: Int) {
        val team = data[position]
        holder.mTeamName.text = team.teamName
        try{
            holder.mTeamName.setTextColor(Color.parseColor(team.teamPrimaryColor))
            holder.mTeamSub.setHintTextColor(Color.parseColor(team.teamSecondaryColor))
        }catch(a: IllegalArgumentException){
            a.printStackTrace()
        }
        Glide.with(holder.mTeamLogo).load(team.teamIcon).into(holder.mTeamLogo)
        holder.mTeamSub.isChecked = team.isSubbed
        holder.mTeamSub.setOnClickListener{
            team.isSubbed = !team.isSubbed
            callBack.updateTeam(position, team)
        }
    }

    class QueryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val mTeamName: TextView = itemView.findViewById(R.id.tv_team_name)
        val mTeamLogo: ImageView = itemView.findViewById(R.id.iv_team_logo)
        val mTeamSub: CheckBox = itemView.findViewById(R.id.cb_subscribe)

    }

}