package com.example.picashu.model

data class Card(val id : String,val set: String,val name :String,val image : String, val serie :String){
    constructor() : this("", "", "", "","")
}
