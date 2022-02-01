package com.example.picashu.viewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.picashu.model.Card
import com.example.picashu.model.CardSetResponse.CardSet
import com.example.picashu.repository.FirebaseRepository
import com.example.picashu.repository.PokemonApiRepository
import com.google.firebase.firestore.EventListener
import kotlinx.coroutines.launch

class PokemonCollectionFragmentViewModel : ViewModel() {

    private var pokemonApiRepository = PokemonApiRepository()
    private var firebaseRepository = FirebaseRepository()

    var savedCard : MutableLiveData<List<Card>> = MutableLiveData()
    val setResponse : MutableLiveData<CardSet> = pokemonApiRepository.setResponse

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

    fun getPokemonSets (id : String, apiKey : String){
        viewModelScope.launch {
            pokemonApiRepository.getPokemonSet(id,apiKey)
        }
    }

}