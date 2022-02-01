package com.example.picashu.view.fragment

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.picashu.R
import com.example.picashu.databinding.ProfilUserFragmentBinding
import com.example.picashu.model.User
import com.example.picashu.viewModel.MaintProfilFragmentViewModel
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage

class MainProfilFragment : Fragment(R.layout.profil_user_fragment) {

    private lateinit var binding: ProfilUserFragmentBinding
    private lateinit var mViewModel: MaintProfilFragmentViewModel
    private lateinit var fullnameProfile: TextInputEditText
    private lateinit var country: TextInputEditText
    private lateinit var postCode: TextInputEditText
    private lateinit var username:TextView
    private lateinit var currentUser: User
    private lateinit var userPp : ImageView
    var selectedProfilImgUri : Uri? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid

        binding = ProfilUserFragmentBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(this).get(MaintProfilFragmentViewModel::class.java)

        fullnameProfile = binding.fullNameProfile
        country = binding.countryNameProfile
        postCode = binding.postCodeProfil
        username = binding.fullname
        userPp = binding.userProfilImgProfilfrag


        mViewModel.getSavedUserAvis(currentUserId)
        mViewModel.getSavedConcludedTrade(currentUserId)
        updateUserUi()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid

        val updateBtn = binding.updateBtn
        val navigationView = requireActivity().findViewById<NavigationView>(R.id.home_activity_nav_view)
        val headerContainer: View = navigationView!!.getHeaderView(0)
        val mImageView = headerContainer.findViewById<ImageView>(R.id.drawer_imageview_profile)
        val mNameText = headerContainer.findViewById<TextView>(R.id.drawer_username)

        updateBtn.setOnClickListener {

            if (country.text != null) {
                mViewModel.UpdateCountry(country.text.toString(), currentUserId)
            }

            if (postCode.text != null) {
                mViewModel.UpdatePostCode(postCode.text.toString(), currentUserId)
            }

            if(fullnameProfile.text.toString() != currentUser.username){
                username.text = fullnameProfile.text.toString()
                mNameText.text = fullnameProfile.text.toString()
                mViewModel.UpdateUsername(fullnameProfile.text.toString(), currentUserId)
                Log.d("MainProfilFrag","changedUsername  :  ${fullnameProfile.text.toString()}")
            }

            Log.d("MainProfilFrag","username  :  ${currentUser.username}")

            if (selectedProfilImgUri != null){
                uploadImageToFirebaseStorage()

                Glide.with(requireActivity())
                    .load(Uri.parse(selectedProfilImgUri.toString()))
                    .apply(RequestOptions.circleCropTransform())
                    .into(mImageView)

            }
        }


        userPp.setOnClickListener {
            retrievSelectedImg()
        }
    }

    fun updateUserUi() {

        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid

        val email = binding.email
        val nbTrade = binding.exchangeLabel
        val nbComment = binding.commentLabel

        mViewModel.getUser(currentUserId).observe(viewLifecycleOwner, Observer {
            currentUser = it

            fullnameProfile.setText(currentUser.username)
            username.text = currentUser.username
            email.text = currentUser.email

            if (currentUser.country != "") {
                country.setText(currentUser.country)
            }

            if (currentUser.postCode != "") {
                postCode.setText(currentUser.postCode)
            }

            Glide.with(this)
                .load(Uri.parse(currentUser.profileImageUrl))
                .apply(RequestOptions.circleCropTransform())
                .into(userPp)

        })


        mViewModel.concludedTrade.observe(viewLifecycleOwner, Observer {
            nbTrade.text = it.size.toString()
        })

        mViewModel.savedAvis.observe(viewLifecycleOwner, Observer {
            nbComment.text = it.size.toString()
        })
    }

    private fun uploadImageToFirebaseStorage() {
        if (selectedProfilImgUri == null) return

        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid

        val filename = FirebaseAuth.getInstance().uid
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedProfilImgUri!!)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "Successfully uploaded image: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener { it1 ->
                    Log.d(ContentValues.TAG, "File Location: $it1")

                    mViewModel.UpdateUserImg(it1.toString(),currentUserId)
                    //saveUserToFirebaseDatabase(it1.toString())
                }
            }
            .addOnFailureListener {
                Log.d(ContentValues.TAG, "Failed to upload image to storage: ${it.message}")
            }
    }


    private fun retrievSelectedImg(){
        Log.d(ContentValues.TAG, "Try to show photo selector")
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultLauncher.launch(intent)

    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK && result != null) {
            // There are no request codes
            selectedProfilImgUri = result.data?.data

            val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, selectedProfilImgUri)

            userPp.setImageBitmap(bitmap)
            //userPp.alpha = 0f
        }
    }

}