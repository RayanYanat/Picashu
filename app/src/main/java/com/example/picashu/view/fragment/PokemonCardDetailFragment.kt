
package com.example.picashu.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.picashu.R
import com.example.picashu.databinding.DetailCardFragmentBinding
import com.example.picashu.model.DataItem
import com.example.picashu.model.ResultsItem
import com.example.picashu.model.TradeCard
import com.example.picashu.view.TradeCardAdapter
import com.example.picashu.viewModel.PokemonApiViewModel

class PokemonCardDetailFragment : Fragment(R.layout.detail_card_fragment),
    TradeCardAdapter.ItemClickListener {

    private lateinit var binding : DetailCardFragmentBinding
    private lateinit var mViewModel: PokemonApiViewModel
    private lateinit var mainImageView: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter : TradeCardAdapter

    private val USER_ID = "USER_ID"


    private var listTradeOffer = ArrayList<TradeCard>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailCardFragmentBinding.inflate(inflater, container, false)
        mainImageView = binding.pokemonCardMainPic
        recyclerView = binding.recyclerViewDataTrade
        mViewModel = ViewModelProvider(this).get(PokemonApiViewModel::class.java)

        val currentCardId = requireArguments().getString("POKE_ID")
        Log.d("pokemonSelectedCardID", "cardId : $currentCardId ")
        mViewModel.getSavedCardTradeOffer(currentCardId!!)
        mViewModel.getPokemonCardWithID(currentCardId!!)
        mViewModel.cardIdResponse.observe(viewLifecycleOwner,{
            val cardImage = it.data?.images?.small
            Log.d("pokemonSelectedCardID", "cardId : $cardImage ")
            Glide.with(this).load(cardImage).into(mainImageView)
        })

        retrieveListCardTrade()
        configureRecyclerView()
        return binding.root
    }

    private fun configureRecyclerView(){
        adapter = TradeCardAdapter(listTradeOffer,this)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        adapter.setResults(listTradeOffer)
    }

    private fun retrieveListCardTrade(){
      mViewModel.savedTradeCardOffer.observe(viewLifecycleOwner,{
          listTradeOffer.addAll(it)
          adapter.notifyDataSetChanged()
      })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }



    override fun onItemClickListener(poke: TradeCard) {
        val bundle = Bundle()
        bundle.putString(USER_ID,poke.userId)
        val profilUserFragment = ProfilUserFragment()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        profilUserFragment.arguments = bundle
        transaction.replace(R.id.main_fragment, profilUserFragment).commit()
    }


}