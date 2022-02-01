package com.example.picashu.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.picashu.Trade
import com.example.picashu.model.Avis
import com.example.picashu.model.User
import com.example.picashu.repository.FirebaseRepository
import com.example.picashu.repository.PokemonApiRepository

class LatestMsgFragmentViewModel : ViewModel() {

    private var pokemonApiRepository = PokemonApiRepository()
    private var firebaseRepository = FirebaseRepository()

    private var currentUser: MutableLiveData<User> = MutableLiveData()
    var currentTradeCard : MutableLiveData<Trade> = MutableLiveData()


    fun getUser (uid: String) : LiveData<User> {
        val curreentUserr = MutableLiveData<User>()
        firebaseRepository.getUser(uid).addOnSuccessListener {
            val userItem = it?.toObject(User::class.java)
            currentUser.value = userItem
            curreentUserr.value = userItem!!
        }
        return curreentUserr
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

    fun createAvis (avis: Avis, toId : String){
        firebaseRepository.CreateAvis(avis,toId)
    }

    fun addConcludedTrade (uid : String , trade : Trade){
        firebaseRepository.addConcludedTrade(uid,trade)
    }
}