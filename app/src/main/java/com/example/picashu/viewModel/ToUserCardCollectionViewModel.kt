package com.example.picashu.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.picashu.model.Card
import com.example.picashu.repository.FirebaseRepository
import com.example.picashu.repository.PokemonApiRepository

class ToUserCardCollectionViewModel : ViewModel() {

    private var pokemonApiRepository = PokemonApiRepository()
    private var firebaseRepository = FirebaseRepository()

    var savedCardBySerie : MutableLiveData<List<Card>> = MutableLiveData()
    var savedCardBySet : MutableLiveData<List<Card>> = MutableLiveData()

    fun getTradeCardListBySerie (uid:String, serie : String): LiveData<List<Card>> {

        firebaseRepository.getCurrentTradedCardBySerie(uid, serie).addOnSuccessListener{

            val savedCardList : MutableList<Card> = mutableListOf()
            if (it != null) {
                for (doc in it) {
                    val cardItem = doc.toObject(Card::class.java)
                    Log.d("PokemonApiViewModel", "carte : $cardItem ")
                    savedCardList.add(cardItem)
                }
            }
            savedCardBySerie.value = savedCardList

        }
        return savedCardBySerie
    }

    fun getTradeCardListBySet (uid:String, set : String): LiveData<List<Card>>{

        firebaseRepository.getCurrentTradedCardBySet(uid, set).addOnSuccessListener{

            val savedCardList : MutableList<Card> = mutableListOf()
            if (it != null) {
                for (doc in it) {
                    val cardItem = doc.toObject(Card::class.java)
                    savedCardList.add(cardItem)
                }
            }
            savedCardBySet.value = savedCardList

        }
        return savedCardBySet
    }

}