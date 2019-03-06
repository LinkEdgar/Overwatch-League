package com.example.enduser.overwatchleague

import android.os.Parcel
import android.os.Parcelable

data class OverwatchTeam(var teamName: String, var teamIcon: String? = null, var teamPrimaryColor: String? = null, var teamSecondaryColor: String? = null, var isSubbed: Boolean = false,
                    var teamId:String? = null, var matchWin: String? = null, var matchLoss:String? = null, var matchDraw:String? = null, var gameWin:String? = null
                    , var gameLoss:String? = null, var gameTie:String? = null): Parcelable{

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(teamName)
        parcel.writeString(teamIcon)
        parcel.writeString(teamPrimaryColor)
        parcel.writeString(teamSecondaryColor)
        parcel.writeByte(if (isSubbed) 1 else 0)
        parcel.writeString(teamId)
        parcel.writeString(matchWin)
        parcel.writeString(matchLoss)
        parcel.writeString(matchDraw)
        parcel.writeString(gameWin)
        parcel.writeString(gameLoss)
        parcel.writeString(gameTie)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OverwatchTeam> {
        override fun createFromParcel(parcel: Parcel): OverwatchTeam {
            return OverwatchTeam(parcel)
        }

        override fun newArray(size: Int): Array<OverwatchTeam?> {
            return arrayOfNulls(size)
        }
    }

}