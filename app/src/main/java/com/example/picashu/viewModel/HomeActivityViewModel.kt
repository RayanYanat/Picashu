package com.example.picashu.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.picashu.model.User
import com.example.picashu.repository.FirebaseRepository

class HomeActivityViewModel : ViewModel () {

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

}