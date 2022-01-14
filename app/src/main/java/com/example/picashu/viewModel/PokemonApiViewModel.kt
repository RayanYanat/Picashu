package com.example.picashu.viewModel

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.example.picashu.PokemonApiService
import com.example.picashu.Trade
import com.example.picashu.model.*
import com.example.picashu.model.CardResponse.ResponseCard
import com.example.picashu.model.CardSetResponse.CardSet
import com.example.picashu.repository.FirebaseRepository
import com.example.picashu.repository.PokemonApiRepository
import com.google.firebase.firestore.EventListener
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PokemonApiViewModel(app: Application)  : AndroidViewModel(app) {

    private var pokemonApiRepository = PokemonApiRepository(app)
    private var firebaseRepository = FirebaseRepository()
    private var currentUser: MutableLiveData<User> = MutableLiveData()

    val response : MutableLiveData<PokemonListeResponse>
    val cardResponse : MutableLiveData<Response>
    val cardIdResponse : MutableLiveData<ResponseCard>
    val setResponse : MutableLiveData<CardSet>


    var currentTradeCard : MutableLiveData<Trade> = MutableLiveData()
    var savedAvis  : MutableLiveData<List<Avis>> = MutableLiveData()
    var savedCard : MutableLiveData<List<Card>> = MutableLiveData()
    var savedTradeCardOffer : MutableLiveData<List<TradeCard>> = MutableLiveData()
    var concludedTrade : MutableLiveData<List<Trade>> = MutableLiveData()
    var savedTradeCardFromSet : MutableLiveData<Int> = MutableLiveData()
    var savedTradeCardFromSeries : MutableLiveData<Int> = MutableLiveData()

    init {
        response = pokemonApiRepository.response
        cardResponse = pokemonApiRepository.cardResponse
        cardIdResponse = pokemonApiRepository.cardIdResponse
        setResponse = pokemonApiRepository.setResponse
    }

     fun getPokemonIds( limit : Int, offset : Int) {
         viewModelScope.launch {
             pokemonApiRepository.getPokemonID(limit, offset)
         }
    }

    fun getPokemonCards (name : String, apiKey : String){
        viewModelScope.launch {
            pokemonApiRepository.getPokemonCard(name, apiKey)
        }
    }

    fun getPokemonCardWithID (id : String){
        viewModelScope.launch {
            pokemonApiRepository.getPokemonCardWithId(id)
        }
    }

    fun getPokemonSets (id : String, apiKey : String){
        viewModelScope.launch {
            pokemonApiRepository.getPokemonSet(id,apiKey)
        }
    }

    fun CreateCard (card :Card , uid : String){
        firebaseRepository.CreateCard(card,uid)
    }

    fun DeleteCard (card :Card , uid : String){
        firebaseRepository.DeleteCard(card,uid)
    }

    fun getCurrentTradedCardBySet(uid:String, set : String): MutableLiveData<Int> {
        firebaseRepository.getCurrentTradedCardBySet(uid, set).addOnSuccessListener {
            savedTradeCardFromSet.value =  it?.size()
        }
        return savedTradeCardFromSet
    }

    fun getCurrentTradedCardBySerie(uid:String, serie : String): MutableLiveData<Int> {
        firebaseRepository.getCurrentTradedCardBySerie(uid, serie).addOnSuccessListener {
           savedTradeCardFromSeries.value =  it?.size()
        }
        return savedTradeCardFromSeries
    }
    fun CreateTradeCard (tradeCard: TradeCard, userUid : String,cardId :String){
        firebaseRepository.CreateTradeCardOffer(tradeCard,cardId,userUid)
    }

    fun DeleteTradeCard (tradeCard: TradeCard, userUid : String,cardId :String){
        firebaseRepository.DeleteTradeCardOffer(tradeCard,cardId,userUid)
    }

    fun addConcludedTrade (uid : String , trade : Trade){
        firebaseRepository.addConcludedTrade(uid,trade)
    }

    fun getUser (uid: String) : LiveData<User> {
        val curreentUserr = MutableLiveData<User>()
        firebaseRepository.getUser(uid).addOnSuccessListener {
            val userItem = it?.toObject(User::class.java)
            currentUser.value = userItem
            curreentUserr.value = userItem!!
        }
        return curreentUserr
    }

    fun createAvis (avis: Avis, toId : String){
        firebaseRepository.CreateAvis(avis,toId)
    }

    fun getCurrentTradeCard ( uid: String ,toUid : String) : LiveData<Trade> {
        val curreentTradeCard = MutableLiveData<Trade>()
        firebaseRepository.getCurrentTradedCard(uid,toUid).addOnSuccessListener {
            val tradeItem = it.toObject(Trade::class.java)
            currentTradeCard.value = tradeItem
            curreentTradeCard.value = tradeItem!!
        }
        return curreentTradeCard
    }

    fun getSavedUserCards(uid: String): LiveData<List<Card>> {
        firebaseRepository.getUserCardCollection(uid).addSnapshotListener(EventListener { value, e ->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                savedCard.value = null
                return@EventListener
            }

            val savedUserList : MutableList<Card> = mutableListOf()
            for (doc in value!!) {
                val userItem = doc.toObject(Card::class.java)
                savedUserList.add(userItem)
            }
            savedCard.value = savedUserList
        })

        return savedCard
    }
   fun getSavedUserAvis(uid: String): LiveData<List<Avis>> {
        firebaseRepository.getUserAvisCollection(uid).addSnapshotListener(EventListener { value, e ->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                savedAvis.value = null
                return@EventListener
            }

            val savedUserList : MutableList<Avis> = mutableListOf()
            for (doc in value!!) {
                val userItem = doc.toObject(Avis::class.java)
                savedUserList.add(userItem)
            }
            savedAvis.value = savedUserList
        })

        return savedAvis
    }

    fun getSavedCardTradeOffer(cardUid: String): LiveData<List<TradeCard>> {
        firebaseRepository.getTradOfferCollection(cardUid).addSnapshotListener(EventListener { value, e ->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                savedTradeCardOffer.value = null
                return@EventListener
            }

            val savedUserList : MutableList<TradeCard> = mutableListOf()
            for (doc in value!!) {
                val userItem = doc.toObject(TradeCard::class.java)
                savedUserList.add(userItem)
            }
            savedTradeCardOffer.value = savedUserList
        })

        return savedTradeCardOffer
    }

    fun getSavedConcludedTrade (uid: String): LiveData<List<Trade>>{
        firebaseRepository.getConcludedTradeCollection(uid).addSnapshotListener(EventListener { value, e ->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                concludedTrade.value = null
                return@EventListener
            }

            val concludedTradeList : MutableList<Trade> = mutableListOf()
            for (doc in value!!) {
                val concludedTradeItem = doc.toObject(Trade::class.java)
                concludedTradeList.add(concludedTradeItem)
            }
            concludedTrade.value = concludedTradeList
        })

        return concludedTrade
    }

}