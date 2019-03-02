package com.example.enduser.overwatchleague

import android.os.Parcel
import android.os.Parcelable

class OverwatchTeam(var teamName: String, var teamIcon: String? = null, var teamPrimaryColor: String? = null, var teamSecondaryColor: String? = null, var isSubbed: Boolean = false): Parcelable{
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(teamName)
        parcel.writeString(teamIcon)
        parcel.writeString(teamPrimaryColor)
        parcel.writeString(teamSecondaryColor)
        parcel.writeByte(if (isSubbed) 1 else 0)
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