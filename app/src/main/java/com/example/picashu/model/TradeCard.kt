package com.example.picashu.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TradeCard(val carId : String, val username : String? , val versionCard :String,val etatCard : String, val cardComment : String, val cardLanguage : String, val userId : String , val profilImg :String?) : Parcelable {
    constructor() : this ("","","","","","","","")
}
