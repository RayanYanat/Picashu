package com.example.picashu.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.picashu.R
import com.example.picashu.databinding.CollectionFragmentListBinding
import com.example.picashu.model.CardSetResponse.DataItem
import com.example.picashu.model.ResultsItem
import com.example.picashu.view.PokemonSetAdapter
import com.example.picashu.viewModel.PokemonApiViewModel

class PokemonCollectionFragment : Fragment(R.layout.collection_fragment_list), PokemonSetAdapter.ItemClickListener {

    private lateinit var binding: CollectionFragmentListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var mViewModel: PokemonApiViewModel
    private lateinit var adapter: PokemonSetAdapter

    private var listPokemonSetData = ArrayList<DataItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CollectionFragmentListBinding.inflate(inflater, container, false)
        val view = binding.root
        mViewModel = ViewModelProvider(this).get(PokemonApiViewModel::class.java)
        recyclerView = binding.recyclerViewSetData
        mViewModel.getPokemonSets("","")
        pokemonSetListApiCall()
        configureRecyclerView()
        return view
    }

    private fun configureRecyclerView() {
        adapter = PokemonSetAdapter(listPokemonSetData, this)
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        adapter.setResults(listPokemonSetData)
    }

    private fun pokemonSetListApiCall() {
        mViewModel.setResponse.observe(viewLifecycleOwner,{
            listPokemonSetData.addAll(it.data as Collection<DataItem>)
            adapter.notifyDataSetChanged()
        })
    }

    override fun onItemClickListener(poke: ResultsItem) {
        Log.d("pokemonADAPTER", "item clicked !! ")    }

}