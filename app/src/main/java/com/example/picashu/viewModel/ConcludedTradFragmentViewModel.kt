package com.example.picashu.viewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.picashu.Trade
import com.example.picashu.repository.FirebaseRepository
import com.google.firebase.firestore.EventListener

class ConcludedTradFragmentViewModel : ViewModel() {

    private var firebaseRepository = FirebaseRepository()

    var concludedTrade : MutableLiveData<List<Trade>> = MutableLiveData()

    fun getSavedConcludedTrade (uid: String): LiveData<List<Trade>> {
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