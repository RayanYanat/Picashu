package com.example.picashu.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.picashu.R
import com.example.picashu.databinding.PokemonListFragmentBinding
import com.example.picashu.model.ResultsItem
import com.example.picashu.view.PokemonListAdapter
import com.example.picashu.viewModel.PokemonApiViewModel

class PokemonListFragment : Fragment(R.layout.pokemon_list_fragment) {

    private lateinit var binding: PokemonListFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var mViewModel: PokemonApiViewModel
    private lateinit var adapter: PokemonListAdapter


    private lateinit var listPokemonData: List<ResultsItem>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding  = PokemonListFragmentBinding.inflate(inflater,container,false)
        val view = binding.root
        mViewModel = ViewModelProvider(this).get(PokemonApiViewModel::class.java)
        recyclerView = binding.recyclerViewDataStat



        configureRecyclerView()
        return view
    }

    private fun configureRecyclerView() {
        this.listPokemonData = ArrayList()
        adapter= PokemonListAdapter()
        recyclerView.layoutManager = GridLayoutManager(activity,3)
        recyclerView.adapter = adapter
    }

    private fun updateUI(results: List<ResultsItem>) {

        adapter.setResults(results)
        adapter.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PokemonListFragmentBinding.bind(view)

        mViewModel.getPokemonIds(20,0)

        mViewModel.response.observe(viewLifecycleOwner, Observer {
            listPokemonData = it.results as List<ResultsItem>
            updateUI(listPokemonData)
        })



    }
}

