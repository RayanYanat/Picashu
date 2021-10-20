package com.example.picashu.viewModel

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.example.picashu.PokemonApiService
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




    var savedCard : MutableLiveData<List<Card>> = MutableLiveData()
    var savedTradeCardOffer : MutableLiveData<List<TradeCard>> = MutableLiveData()

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

    fun CreateTradeCard (tradeCard: TradeCard, userUid : String,cardId :String){
        firebaseRepository.CreateTradeCardOffer(tradeCard,cardId,userUid)
    }

    fun DeleteTradeCard (tradeCard: TradeCard, userUid : String,cardId :String){
        firebaseRepository.DeleteTradeCardOffer(tradeCard,cardId,userUid)
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

}