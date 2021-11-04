package com.example.picashu.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Card(val id : String,val set: String,val name :String,val image : String, val serie :String): Parcelable {
    constructor() : this("", "", "", "","")
}
