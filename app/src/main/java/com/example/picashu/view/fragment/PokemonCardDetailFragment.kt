package com.example.picashu.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.picashu.R
import com.example.picashu.databinding.DetailCardFragmentBinding
import com.example.picashu.viewModel.PokemonApiViewModel

class PokemonCardDetailFragment : Fragment(R.layout.detail_card_fragment) {

    private lateinit var binding : DetailCardFragmentBinding
    private lateinit var mViewModel: PokemonApiViewModel
    private lateinit var mainImageView: ImageView



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailCardFragmentBinding.inflate(inflater, container, false)
        mainImageView = binding.pokemonCardMainPic
        mViewModel = ViewModelProvider(this).get(PokemonApiViewModel::class.java)

        val currentCardId = requireArguments().getString("POKE_ID")
        Log.d("pokemonSelectedCardID", "cardId : $currentCardId ")
        mViewModel.getPokemonCardWithID(currentCardId!!)
        mViewModel.cardIdResponse.observe(viewLifecycleOwner,{
            val cardImage = it.data?.images?.small
            Log.d("pokemonSelectedCardID", "cardId : $cardImage ")
            Glide.with(this).load(cardImage).into(mainImageView)
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}