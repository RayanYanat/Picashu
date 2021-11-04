package com.example.picashu.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.picashu.R
import com.example.picashu.databinding.UserProfilFragmentBinding
import com.example.picashu.model.Avis
import com.example.picashu.model.Card
import com.example.picashu.model.User
import com.example.picashu.view.AvisTradeAdapter
import com.example.picashu.view.PokemonCardAdapter
import com.example.picashu.view.activity.ChatLogActivity
import com.example.picashu.viewModel.PokemonApiViewModel
import com.google.firebase.auth.FirebaseAuth

class ProfilUserFragment: Fragment(R.layout.user_profil_fragment) {

    private lateinit var binding : UserProfilFragmentBinding
    private lateinit var mViewModel: PokemonApiViewModel
    private lateinit var userProfilImg : ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AvisTradeAdapter

    private var listUserAvis = ArrayList<Avis>()

    private lateinit var selectedUserFromTrade : User
    
    private var fromUser : User? = null
    private var currentCard : Card? = null

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
        userProfilImg = binding.userProfilImgTrade
        recyclerView = binding.avisRecyclerView


        val currentUserId = FirebaseAuth.getInstance().uid
        currentCard = requireArguments().getParcelable<Card>("POKE_CARD")

        val selectedUserId = requireArguments().getString("USER_ID")
        if (selectedUserId != null) {
            Log.d("profilUserID", "UserId : $currentUserId ")

            mViewModel.getUser(selectedUserId).observe(viewLifecycleOwner,{
                selectedUserFromTrade = it
                binding.usernameTradeUser.text = it.username

                Glide.with(this)
                    .load(Uri.parse(it.profileImageUrl))
                    .apply(RequestOptions.circleCropTransform())
                    .into(userProfilImg)

                mViewModel.getCurrentTradedCardBySet(selectedUserFromTrade!!.uid!!, currentCard!!.set).observe(viewLifecycleOwner,{
                    Log.d("profilUserSetCardNB", "NOMBRE : $it ")
                    Log.d("profilUserSetCardNB", "NOMBRE : ${selectedUserFromTrade?.uid} ")
                })

                mViewModel.getCurrentTradedCardBySerie(selectedUserFromTrade!!.uid!!, currentCard!!.serie).observe(viewLifecycleOwner,{
                    Log.d("profilUserSerieCardNB", "NOMBRE serie: $it ")
                    Log.d("profilUserSerieCardNB", "NOMBRE serie: ${selectedUserFromTrade?.uid} ")
                })

                mViewModel.getUser(currentUserId!!).observe(viewLifecycleOwner,{
                    fromUser = it
                })

                mViewModel.getSavedUserAvis(selectedUserFromTrade!!.uid!!)
                retrieveUserAvis()
                configureRecyclerView()
            })

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      //  Log.d("profilUserSelected", "NOMBRE : $selectedUserFromTrade ")

        //Log.d("selectedUserOnView", "selectedUser : $selectedUserFromTrade ")


        binding.sendMsgButton.setOnClickListener {
            Log.d("profilUserSelected", "NOMBRE : $selectedUserFromTrade ")

            val intent = Intent(view.context, ChatLogActivity::class.java)
            intent.putExtra(TO_USER_KEY,selectedUserFromTrade)
            intent.putExtra(FROM_USER_KEY,fromUser)
            startActivity(intent)

        }
    }

    fun retrieveUserAvis(){
        mViewModel.savedAvis.observe(viewLifecycleOwner, {
           listUserAvis.addAll(it)
            adapter.notifyDataSetChanged()
            Log.d("profilUserListAvis", "listAvis: $it ")
        })
    }

    fun configureRecyclerView(){

        adapter = AvisTradeAdapter()
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        adapter.setResults(listUserAvis)

    }
}