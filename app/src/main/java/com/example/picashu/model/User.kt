package com.example.picashu.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(var username: String , var uid: String?,var profileImageUrl: String, var email : String, var country : String , var postCode :String,var online : Boolean): Parcelable {

    constructor() : this("", "", "","","","",false)
}

