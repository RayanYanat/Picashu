package com.example.picashu.model

data class Avis(val communicationRate : Float,val livraisonRate : Float, val avis : String,val fromId : String,val fromImgUser : String, val fromUsername :String){
    constructor() : this (0f,0f,"","","","")

}
