package com.example.picashu.model

import android.os.Parcelable

data class User(var username: String , var uid: String?,var profileImageUrl: String, var email : String ) {

    constructor() : this("", "", "","")
}

