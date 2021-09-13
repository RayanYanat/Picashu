package com.example.picashu.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.picashu.R
import com.example.picashu.databinding.CardFragmentListBinding
import com.example.picashu.model.DataItem
import com.example.picashu.model.ResultsItem
import com.example.picashu.view.PokemonListAdapter
import com.example.picashu.view.pokemonCardAdapter
import com.example.picashu.viewModel.PokemonApiViewModel


class PokemonCardListFragment : Fragment(R.layout.card_fragment_list),pokemonCardAdapter.ItemClickListener {

    private lateinit var binding: CardFragmentListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var mViewModel: PokemonApiViewModel
    private lateinit var adapter: pokemonCardAdapter

    private var listPokemonCardData = ArrayList<DataItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CardFragmentListBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(this).get(PokemonApiViewModel::class.java)
        recyclerView = binding.recyclerViewCardData

        val currentCardName = requireArguments().getString("POKE_NAME")
        Log.d("updateUI", "currentCardName:$currentCardName")
        val currentCardNameFor = "name:$currentCardName"

        mViewModel.getPokemonCards(currentCardNameFor,"")

        pokemonCardListApiCall()
        configureRecyclerView()

        return binding.root
    }

    private fun configureRecyclerView(){
        adapter = pokemonCardAdapter(listPokemonCardData,this)
        val layoutManager = GridLayoutManager(activity, 3)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        adapter.setResults(listPokemonCardData)
    }

    private fun pokemonCardListApiCall() {

        mViewModel.cardResponse.observe(viewLifecycleOwner, Observer {
            val result = it.data
            listPokemonCardData.addAll(result)
            adapter.notifyDataSetChanged()
        })

    }

    override fun onItemClickListener(poke: DataItem) {
        TODO("Not yet implemented")
    }

}