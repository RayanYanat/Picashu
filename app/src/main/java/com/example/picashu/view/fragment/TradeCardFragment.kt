package com.example.picashu.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.picashu.R
import com.example.picashu.databinding.TradeCardFragmentBinding
import kotlinx.android.synthetic.main.trade_card_fragment.*

class TradeCardFragment : Fragment(R.layout.trade_card_fragment) {

    private lateinit var binding : TradeCardFragmentBinding
    private lateinit var mainImageView: ImageView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TradeCardFragmentBinding.inflate(inflater, container, false)
        mainImageView = binding.pokemonTradeCardMainPic

        val currentCardId = requireArguments().getString("POKE_ID")
        Glide.with(this).load(currentCardId).into(mainImageView)


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

        val tradeBtn = binding.tradeBtn
        tradeBtn.setOnClickListener {

            val stateOfCard = card_etat.text.toString()
            val cardLangue = card_langue.text.toString()
            val versionCard = version_card.text.toString()


        }

    }
}