package com.example.picashu.view.activity

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.picashu.databinding.ActivityRegisterBinding
import com.example.picashu.viewModel.RegisterActivityViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityRegisterBinding
    var selectedProfilImgUri : Uri? = null
    var profilImgUrl : String? = null
    lateinit var mViewModel: RegisterActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uid = FirebaseAuth.getInstance().currentUser?.uid
        Log.d("RegisterActivity","uid  :  $uid")

        mViewModel = ViewModelProvider(this).get(RegisterActivityViewModel::class.java)
        binding.registerButtonRegister.setOnClickListener(){
            performRegister()
        }

        binding.alreadyHaveAccountTextView.setOnClickListener {
            Log.d(TAG, "Try to show login activity")
            // launch the login activity somehow
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.selectphotoButtonRegister.setOnClickListener(){
            //open gallery and allow user to pick img
            retrievSelectedImg()
        }
    }

    //register user in firebase for email autentication
    private fun performRegister(){
        val email = binding.emailEdittextRegister.text.toString()
        val password = binding.passwordEdittextRegister.text.toString()
        val username = binding.usernameEdittextRegister.text.toString()
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        Log.d("RegisterActivity","email and password : $email and $password and $uid")

        if (password.isEmpty() || email.isEmpty() || username.isEmpty()){
            Toast.makeText(this,"please enter a email, a password and a username",Toast.LENGTH_SHORT).show()
            return
        }

        // Firebase Authentication to create a user with email and password
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            if(!it.isSuccessful) return@addOnCompleteListener
            else{

                uploadImageToFirebaseStorage()
                Toast.makeText(this, "Succeed to create user: $uid", Toast.LENGTH_SHORT).show()


            }
        }.addOnFailureListener {
            Log.d("RegisterActivity","failed to create a user: ${it.message}")
            Toast.makeText(this, "Failed to create user: ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadImageToFirebaseStorage() {
        if (selectedProfilImgUri == null) return

        val filename = FirebaseAuth.getInstance().uid
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedProfilImgUri!!)
            .addOnSuccessListener {
                Log.d(TAG, "Successfully uploaded image: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener { it1 ->
                    Log.d(TAG, "File Location: $it1")

                    saveUserToFirebaseDatabase(it1.toString())
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "Failed to upload image to storage: ${it.message}")
            }
    }

    private fun saveUserToFirebaseDatabase(profileImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val email = binding.emailEdittextRegister.text.toString()
        val username = binding.usernameEdittextRegister.text.toString()

        mViewModel.createUserToFirebase(username,uid,profileImageUrl.toString(),email)
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)

    }

    private fun retrievSelectedImg(){
        Log.d(TAG, "Try to show photo selector")
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultLauncher.launch(intent)

    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK && result != null) {
            // There are no request codes
            selectedProfilImgUri = result.data?.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedProfilImgUri)

            binding.selectphotoImageviewRegister.setImageBitmap(bitmap)
            binding.selectphotoButtonRegister.alpha = 0f
        }
    }

}
