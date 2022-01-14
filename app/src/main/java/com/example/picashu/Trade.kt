package com.example.picashu

data class Trade(var fromId: String, var toId: String, val versionCard :String,val cardImg : String, val etatCard : String,val cardLanguage : String, val date : String){
    constructor() : this ("","","","","","","")
}
