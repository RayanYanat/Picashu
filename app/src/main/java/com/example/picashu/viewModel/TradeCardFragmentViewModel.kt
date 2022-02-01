package com.example.picashu.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.picashu.model.TradeCard
import com.example.picashu.model.User
import com.example.picashu.repository.FirebaseRepository
import com.example.picashu.repository.PokemonApiRepository

class TradeCardFragmentViewModel : ViewModel() {

    private var pokemonApiRepository = PokemonApiRepository()
    private var firebaseRepository = FirebaseRepository()

    private var currentUser: MutableLiveData<User> = MutableLiveData()


    fun getUser (uid: String) : LiveData<User> {
        val curreentUserr = MutableLiveData<User>()
        firebaseRepository.getUser(uid).addOnSuccessListener {
            val userItem = it?.toObject(User::class.java)
            currentUser.value = userItem
            curreentUserr.value = userItem!!
        }
        return curreentUserr
    }

    fun CreateTradeCard (tradeCard: TradeCard, userUid : String, cardId :String){
        firebaseRepository.CreateTradeCardOffer(tradeCard,cardId,userUid)
    }


}