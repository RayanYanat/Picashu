package com.example.picashu.viewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.picashu.model.CardResponse.ResponseCard
import com.example.picashu.model.TradeCard
import com.example.picashu.repository.FirebaseRepository
import com.example.picashu.repository.PokemonApiRepository
import com.google.firebase.firestore.EventListener
import kotlinx.coroutines.launch

class PokemonCardDetailFragmentViewModel : ViewModel() {

    private var pokemonApiRepository = PokemonApiRepository()
    private var firebaseRepository = FirebaseRepository()

    var savedTradeCardOffer : MutableLiveData<List<TradeCard>> = MutableLiveData()
    val cardIdResponse : MutableLiveData<ResponseCard> = pokemonApiRepository.cardIdResponse


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

    fun getPokemonCardWithID (id : String){
        viewModelScope.launch {
            pokemonApiRepository.getPokemonCardWithId(id)
        }
    }

}