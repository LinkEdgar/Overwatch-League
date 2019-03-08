package com.example.enduser.overwatchleague.Views.Adapters

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.enduser.overwatchleague.POJOs.OverwatchTeam
import com.example.enduser.overwatchleague.R

class SubscribedAdapter(var context: Context, var mData: ArrayList<OverwatchTeam>, var mCallback: OnClickCallback): RecyclerView.Adapter<SubscribedAdapter.SubscribeViewHolder>(){

    interface OnClickCallback{
        fun onClick(url: String?)
    }
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SubscribeViewHolder {
        return SubscribeViewHolder(LayoutInflater.from(context).inflate(R.layout.team_container, parent, false))
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: SubscribeViewHolder, position: Int) {
        val team = mData[position]
        holder.mTeamName.text = team.teamName
        Glide.with(holder.mTeamImage).load(team.teamIcon).into(holder.mTeamImage)
        try{
            holder.mTeamName.setTextColor(Color.parseColor(team.teamPrimaryColor))
            //holder.mTeamSub.setHintTextColor(Color.parseColor(team.teamSecondaryColor))
        }catch(a: IllegalArgumentException){
            a.printStackTrace()
        }
        holder.mTeamWins.text = team.matchWin
        holder.mTeamLoss.text = team.matchLoss
        holder.mTeamDraw.text = team.matchDraw
        holder.mTeamGameWin.text = team.gameWin
        holder.mTeamGameLoss.text = team.gameLoss
        holder.mTeamGameTie.text = team.gameTie
        holder.twitter.setOnClickListener{mCallback.onClick(team.accountTwitter)}
        holder.facebook.setOnClickListener{mCallback.onClick(team.accountFacebook)}
        holder.youtube.setOnClickListener{mCallback.onClick(team.accountYoutube)}
        holder.instagram.setOnClickListener{mCallback.onClick(team.accountInstagram)}


    }

    class SubscribeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val mTeamName: TextView = itemView.findViewById(R.id.tv_team_name)
        val mTeamImage: ImageView = itemView.findViewById(R.id.iv_team_image)
        val mTeamWins: TextView = itemView.findViewById(R.id.tv_team_win)
        val mTeamLoss: TextView = itemView.findViewById(R.id.tv_team_loss)
        val mTeamDraw: TextView = itemView.findViewById(R.id.tv_team_draw)
        val mTeamGameWin: TextView = itemView.findViewById(R.id.tv_game_wins)
        val mTeamGameLoss: TextView = itemView.findViewById(R.id.tv_game_loss)
        val mTeamGameTie: TextView = itemView.findViewById(R.id.tv_game_tie)
        val twitter: ImageView =itemView.findViewById(R.id.twitter)
        val facebook: ImageView =itemView.findViewById(R.id.facebook)
        val youtube: ImageView =itemView.findViewById(R.id.youtube)
        val instagram: ImageView =itemView.findViewById(R.id.instagram)
    }
}