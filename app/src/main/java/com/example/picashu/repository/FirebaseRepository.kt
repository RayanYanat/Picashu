package com.example.picashu.repository

import com.example.picashu.Trade
import com.example.picashu.model.Avis
import com.example.picashu.model.Card
import com.example.picashu.model.TradeCard
import com.example.picashu.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import java.util.*

class FirebaseRepository {

    private val COLLECTION_NAME = "users"
    private val COLLECTION_MSG = "messages"

    fun getUsersCollection(): CollectionReference {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME)
    }

    fun getMessagesCollection(): CollectionReference {
        return FirebaseFirestore.getInstance().collection(COLLECTION_MSG)
    }

    fun createUser(username: String,uid: String , urlPicture: String?,email:String): Task<Void?> {
        val userToCreate = User(username, uid, urlPicture!!,email,"","",false)
        return getUsersCollection().document(uid).set(userToCreate)
    }

    fun getUser(uid: String): Task<DocumentSnapshot?> {
        return getUsersCollection().document(uid).get()
    }

    fun getCurrentUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }

    fun getCurrentUserId(): String {
        return Objects.requireNonNull(FirebaseAuth.getInstance().currentUser)!!.uid
    }

    fun getCurrentTradedCardBySerie(uid:String, serie : String) : Task<QuerySnapshot?>{
        return FirebaseFirestore.getInstance().collection("/users/$uid/cardCollection").whereEqualTo("serie",serie).get()
    }

    fun getCurrentTradedCard(fromId: String,toId : String): Task<DocumentSnapshot> {
        return FirebaseFirestore.getInstance().collection("/TradeBetweenUsers/$fromId/$toId").document("card").get()
    }

    fun getCurrentTradedCardBySet(uid:String, set : String) : Task<QuerySnapshot?>{
        return FirebaseFirestore.getInstance().collection("/users/$uid/cardCollection").whereEqualTo("set",set).get()
    }

    fun CreateTradeCardOffer (tradeCard : TradeCard, cardUid : String, userUid : String) : Task<Void>{
        return FirebaseFirestore.getInstance().collection("/tradeCardCollection/$cardUid/listTradeCrd").document(userUid).set(tradeCard)
    }

    fun DeleteTradeCardOffer (tradeCard : TradeCard, cardUid : String, userUid : String) : Task<Void>{
        return FirebaseFirestore.getInstance().collection("/tradeCardCollection/$cardUid/listTradeCrd").document(userUid).delete()
    }

    fun getTradOfferCollection (cardUid : String):CollectionReference{
        return  FirebaseFirestore.getInstance().collection("/tradeCardCollection/$cardUid/listTradeCrd")
    }

    fun CreateCard (card :Card, uid:String): Task<Void> {
        return FirebaseFirestore.getInstance().collection("/users/$uid/cardCollection").document(card.id).set(card)
    }

    fun CreateAvis (avis : Avis, toId : String): Task<DocumentReference> {
        return  FirebaseFirestore.getInstance().collection("/users/$toId/avis").add(avis)
    }

    fun DeleteCard(card :Card, uid:String) : Task<Void> {
        return FirebaseFirestore.getInstance().collection("/users/$uid/cardCollection").document(card.id).delete()
    }

    fun UpdateUserCountry (userUid : String, country:String): Task<Void>{
        return FirebaseFirestore.getInstance().collection("users").document(userUid).update("country",country)
    }

    fun UpdateUserPostCode (userUid : String, postCode:String): Task<Void>{
        return FirebaseFirestore.getInstance().collection("users").document(userUid).update("postCode",postCode)
    }

    fun UpdateUserProfilImage(userUid : String, userImagePp:String): Task<Void>{
        return FirebaseFirestore.getInstance().collection("users").document(userUid).update("profileImageUrl",userImagePp)
    }

    fun UpdateUsername (username: String,uid: String): Task<Void>{
        return FirebaseFirestore.getInstance().collection("users").document(uid).update("username",username)
    }

    fun getUserCardCollection(uid:String): CollectionReference{
        return  FirebaseFirestore.getInstance().collection("/users/$uid/cardCollection")
    }

    fun getUserAvisCollection(uid:String): CollectionReference{
        return  FirebaseFirestore.getInstance().collection("/users/$uid/avis")
    }

    fun addConcludedTrade(uid : String, trade : Trade) : Task<DocumentReference> {
        return  FirebaseFirestore.getInstance().collection("/concludedTrade/$uid/trade").add(trade)
    }

    fun getConcludedTradeCollection(uid: String): CollectionReference{
        return FirebaseFirestore.getInstance().collection("/concludedTrade/$uid/trade")
    }
}