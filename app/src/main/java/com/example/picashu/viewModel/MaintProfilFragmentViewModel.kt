package com.example.picashu.viewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.picashu.Trade
import com.example.picashu.model.Avis
import com.example.picashu.model.User
import com.example.picashu.repository.FirebaseRepository
import com.example.picashu.repository.PokemonApiRepository
import com.google.firebase.firestore.EventListener

class MaintProfilFragmentViewModel : ViewModel() {

    private var pokemonApiRepository = PokemonApiRepository()
    private var firebaseRepository = FirebaseRepository()

    var savedAvis  : MutableLiveData<List<Avis>> = MutableLiveData()
    var concludedTrade : MutableLiveData<List<Trade>> = MutableLiveData()
    private var currentUser: MutableLiveData<User> = MutableLiveData()


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

    fun getUser (uid: String) : LiveData<User> {
        val curreentUserr = MutableLiveData<User>()
        firebaseRepository.getUser(uid).addOnSuccessListener {
            val userItem = it?.toObject(User::class.java)
            currentUser.value = userItem
            curreentUserr.value = userItem!!
        }
        return curreentUserr
    }

    fun UpdateCountry (country:String,userId:String){
        firebaseRepository.UpdateUserCountry(userId,country)
    }

    fun UpdatePostCode (postCode:String,userId:String){
        firebaseRepository.UpdateUserPostCode(userId,postCode)
    }

    fun UpdateUsername (username:String,userId:String){
        firebaseRepository.UpdateUsername(username,userId)
    }

    fun UpdateUserImg (userImageProfil:String,userId:String){
        firebaseRepository.UpdateUserProfilImage(userId,userImageProfil)
    }

}