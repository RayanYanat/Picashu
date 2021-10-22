package com.example.picashu.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.picashu.R
import com.example.picashu.databinding.TradeCardFragmentBinding
import com.example.picashu.model.TradeCard
import com.example.picashu.viewModel.PokemonApiViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.trade_card_fragment.*

class TradeCardFragment : Fragment(R.layout.trade_card_fragment) {

    private lateinit var binding : TradeCardFragmentBinding
    private lateinit var mViewModel: PokemonApiViewModel
    private lateinit var mainImageView: ImageView
    private lateinit var currentCardId : String
    private lateinit var currentCardImage : String
    private lateinit var currentUsername : String
    private lateinit var ListAvisUser : String



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TradeCardFragmentBinding.inflate(inflater, container, false)
        mainImageView = binding.pokemonTradeCardMainPic
        mViewModel = ViewModelProvider(this).get(PokemonApiViewModel::class.java)

        currentCardId = requireArguments().getString("POKE_ID").toString()
        currentCardImage = requireArguments().getString("POKE_IMG_URL").toString()
        Glide.with(this).load(currentCardImage).into(mainImageView)


        val language = resources.getStringArray(R.array.Langues)
        val etats = resources.getStringArray(R.array.Etats)
        val version = resources.getStringArray(R.array.Version)

        val languageAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_menu_item,language)
        val etatsAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_menu_item,etats)
        val versionAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_menu_item,version)

        binding.cardEtat.setAdapter(etatsAdapter)
        binding.cardLangue.setAdapter(languageAdapter)
        binding.versionCard.setAdapter(versionAdapter)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid

        mViewModel.getUser(currentUserId).observe(viewLifecycleOwner,{
            if(it != null){
                currentUsername = it.username
            }
        })

        val tradeBtn = binding.tradeBtn
        tradeBtn.setOnClickListener {

            val stateOfCard = card_etat.text.toString()
            val cardLangue = card_langue.text.toString()
            val versionCard = version_card.text.toString()
            val cardComment = trade_card_desc.text.toString()

            val tradeCardToAdd = TradeCard(currentCardId,currentUsername,versionCard,stateOfCard,cardComment,cardLangue,currentUserId)

            mViewModel.CreateTradeCard(tradeCardToAdd,currentUserId,currentCardId)

        }

    }
}