package com.example.picashu.model

data class TradeCard(val carId : String, val username : String? , val versionCard :String,val etatCard : String, val cardComment : String, val cardLanguage : String, val userId : String){
    constructor() : this ("","","","","","","")
}
