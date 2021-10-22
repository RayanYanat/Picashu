package com.example.picashu.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.picashu.R
import com.example.picashu.databinding.UserProfilFragmentBinding
import com.example.picashu.model.User
import com.example.picashu.view.activity.ChatLogActivity
import com.example.picashu.viewModel.PokemonApiViewModel
import com.google.firebase.auth.FirebaseAuth

class ProfilUserFragment: Fragment(R.layout.user_profil_fragment) {

    private lateinit var binding : UserProfilFragmentBinding
    private lateinit var mViewModel: PokemonApiViewModel

    private var selectedUserFromTrade : User? = null
    private var fromUser : User? = null

    companion object {
        const val TO_USER_KEY = "USER_KEY"
        const val FROM_USER_KEY = "FROM_USER_KEY"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UserProfilFragmentBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(this).get(PokemonApiViewModel::class.java)
        val currentUserId = FirebaseAuth.getInstance().uid

        val selectedUserId = requireArguments().getString("USER_ID")
        if (selectedUserId != null) {
            Log.d("profilUserID", "UserId : $currentUserId ")

            mViewModel.getUser(selectedUserId).observe(viewLifecycleOwner,{
                selectedUserFromTrade = it
                binding.usernameTradeUser.text = it.username
            })

            mViewModel.getUser(currentUserId!!).observe(viewLifecycleOwner,{
                fromUser = it
            })
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sendMsgButton.setOnClickListener {

            val intent = Intent(view.context, ChatLogActivity::class.java)
            intent.putExtra(TO_USER_KEY,selectedUserFromTrade)
            intent.putExtra(FROM_USER_KEY,fromUser)
            startActivity(intent)

        }
    }
}