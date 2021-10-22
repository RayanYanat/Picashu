package com.example.picashu.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(var username: String , var uid: String?,var profileImageUrl: String, var email : String ): Parcelable {

    constructor() : this("", "", "","")
}

