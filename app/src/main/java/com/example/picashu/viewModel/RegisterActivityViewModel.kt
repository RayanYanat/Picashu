package com.example.picashu.viewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.picashu.repository.FirebaseRepository

class RegisterActivityViewModel :ViewModel() {

    private var firebaseRepository = FirebaseRepository()

    fun createUserToFirebase(username :String, uid: String, photoUrl : String, email : String,){
        firebaseRepository.createUser(username,uid,photoUrl,email).addOnFailureListener {
            Log.e(ContentValues.TAG,"Failed to save Address!")
        }
    }

}