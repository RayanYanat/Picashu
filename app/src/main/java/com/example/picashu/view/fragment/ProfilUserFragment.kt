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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.picashu.R
import com.example.picashu.databinding.UserProfilFragmentBinding
import com.example.picashu.model.Avis
import com.example.picashu.model.Card
import com.example.picashu.model.TradeCard
import com.example.picashu.model.User
import com.example.picashu.view.adapter.AvisTradeAdapter
import com.example.picashu.view.activity.ChatLogActivity
import com.example.picashu.viewModel.ProfilUserFragmentViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfilUserFragment : Fragment(R.layout.user_profil_fragment) {

    private lateinit var binding: UserProfilFragmentBinding
    private lateinit var mViewModel: ProfilUserFragmentViewModel
    private lateinit var userProfilImg: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AvisTradeAdapter

    private var listUserAvis = ArrayList<Avis>()

    private lateinit var selectedUserFromTrade: User

    private var fromUser: User? = null
    private var currentCard: Card? = null
    private var currentTradedCard: TradeCard? = null

    companion object {
        const val TO_USER_KEY = "USER_KEY"
        const val FROM_USER_KEY = "FROM_USER_KEY"
        const val USER_ID = "USER_ID"
        const val POKE_CARD = "POKE_CARD"
        const val POKE_SET = "POKE_SET"
        const val POKE_SERIES = "POKE_SERIES"
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UserProfilFragmentBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(this).get(ProfilUserFragmentViewModel::class.java)
        userProfilImg = binding.userProfilImgTrade
        recyclerView = binding.avisRecyclerView

        val currentUserId = FirebaseAuth.getInstance().uid

        currentCard = requireArguments().getParcelable<Card>("POKE_CARD")
        currentTradedCard = requireArguments().getParcelable<TradeCard>("USER_ID")

        if (currentTradedCard != null) {

            mViewModel.getSavedConcludedTrade(currentTradedCard!!.userId)



            mViewModel.concludedTrade.observe(viewLifecycleOwner, Observer {
                binding.echangeNbTradeUser.text = "${it.size} échanges"
            })

            mViewModel.getUser(currentTradedCard!!.userId).observe(viewLifecycleOwner, {
                selectedUserFromTrade = it
                mViewModel.getSavedUserAvis(it.uid!!)
                updateProfilUi(it, currentCard!!)
                manageUserPresence()
            })

            mViewModel.getUser(currentUserId!!).observe(viewLifecycleOwner, {
                fromUser = it
            })

           // manageUserPresence()
            retrieveUserAvis()
            configureRecyclerView()


        }

        return binding.root
    }

    fun updateProfilUi(user: User, card: Card) {

        binding.usernameTradeUser.text = user.username

        if (user.country != "" && user.postCode != "") {
            binding.localisationTradeUser.text = "${user.country}(${user.postCode})"
        } else if (user.country != "" && user.postCode == "") {
            binding.localisationTradeUser.text = "${user.country}"
        } else if (user.country == "" && user.postCode != "") {
            binding.localisationTradeUser.text = "${user.postCode}"
        } else if (user.country == "" && user.postCode == "") {
            binding.localisationTradeUser.text = ""
        }

        Glide.with(this)
            .load(Uri.parse(user.profileImageUrl))
            .apply(RequestOptions.circleCropTransform())
            .into(userProfilImg)

        mViewModel.getCurrentTradedCardBySet(user.uid!!, card.set).observe(viewLifecycleOwner, {
            binding.setNameUserProfil.text = "set ${card.set} :"
            binding.lienSetCollections.text = "-> see cards($it)   "
            Log.d("profilUserSetCardNB", "NOMBRE : $it ")
            Log.d("profilUserSetCardNB", "NOMBRE : ${user.uid} ")
        })

        mViewModel.getCurrentTradedCardBySerie(user.uid!!, card.serie).observe(viewLifecycleOwner, {
            binding.seriesNameUserProfil.text = "serie ${card.serie} :"
            binding.lienSeriesCollections.text = "-> see cards($it)"

            Log.d("profilUserSerieCardNB", "NOMBRE serie: $it ")
            Log.d("profilUserSerieCardNB", "NOMBRE serie: ${user.uid} ")
        })

    }


    fun manageUserPresence() {

        val fbquery =
            FirebaseDatabase.getInstance("https://picashu-20d74-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("/status/${selectedUserFromTrade.uid}")
        fbquery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val statut = snapshot.getValue(String::class.java)
                binding.ConnexionTradeUser.text = "statut : $statut"
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //  Log.d("profilUserSelected", "NOMBRE : $selectedUserFromTrade ")

        //Log.d("selectedUserOnView", "selectedUser : $selectedUserFromTrade ")


        binding.sendMsgButton.setOnClickListener {
            Log.d("profilUserSelected", "NOMBRE : $selectedUserFromTrade ")

            val intent = Intent(view.context, ChatLogActivity::class.java)
            intent.putExtra(TO_USER_KEY, selectedUserFromTrade)
            intent.putExtra(FROM_USER_KEY, fromUser)
            intent.putExtra(USER_ID, currentTradedCard)
            intent.putExtra(POKE_CARD, currentCard)
            startActivity(intent)

        }

        binding.lienSetCollections.setOnClickListener {
            val bundle = Bundle()
            val set = currentCard?.set
            val toUserCardCollectionFragment = ToUserCardCollection()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            bundle.putString(POKE_SET, set)
            bundle.putString(USER_ID, selectedUserFromTrade.uid)
            toUserCardCollectionFragment.arguments = bundle
            transaction.replace(R.id.main_fragment, toUserCardCollectionFragment)
                .addToBackStack(null).commit()
        }

        binding.lienSeriesCollections.setOnClickListener {
            val bundle = Bundle()
            val serie = currentCard?.serie
            val toUserCardCollectionFragment = ToUserCardCollection()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            bundle.putString(POKE_SERIES, serie)
            bundle.putString(USER_ID, selectedUserFromTrade.uid)
            toUserCardCollectionFragment.arguments = bundle
            transaction.replace(R.id.main_fragment, toUserCardCollectionFragment)
                .addToBackStack(null).commit()
        }

    }

    fun retrieveUserAvis() {
        mViewModel.savedAvis.observe(viewLifecycleOwner, {
            listUserAvis.clear()
            listUserAvis.addAll(it)
            adapter.notifyDataSetChanged()
            Log.d("profilUserListAvis", "listAvis: $it ")
        })
    }

    fun configureRecyclerView() {

        adapter = AvisTradeAdapter()
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        adapter.setResults(listUserAvis)

    }
}