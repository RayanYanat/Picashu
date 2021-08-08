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
import com.example.picashu.databinding.PokemonListFragmentBinding
import com.example.picashu.model.ResultsItem
import com.example.picashu.view.PokemonListAdapter
import com.example.picashu.viewModel.PokemonApiViewModel

class PokemonListFragment : Fragment(R.layout.pokemon_list_fragment) {

    private lateinit var binding: PokemonListFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var mViewModel: PokemonApiViewModel
    private lateinit var adapter: PokemonListAdapter

    private var readyToChargeList : Boolean = false
    private var offset = 0
    private var limit = 20


    private lateinit var listPokemonData: List<ResultsItem>
    private var listToAddToAdapter = ArrayList<ResultsItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding  = PokemonListFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        mViewModel = ViewModelProvider(this).get(PokemonApiViewModel::class.java)
        recyclerView = binding.recyclerViewDataStat
        mViewModel.getPokemonIds(20,offset)
        pokemonListApiCall()
        configureRecyclerView()

        return view
    }

    private fun configureRecyclerView() {
        this.listPokemonData = ArrayList()
        adapter= PokemonListAdapter()
        val layoutManager = GridLayoutManager(activity, 3)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {

                    val visibleItemCount: Int = layoutManager.childCount
                    val totalItemCount: Int = layoutManager.itemCount
                    val pastVisibleItems: Int = layoutManager.findFirstVisibleItemPosition()

                    //charge data if screen is scrolled
                    if (readyToChargeList) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            readyToChargeList = false
                            offset += 20
                            mViewModel.getPokemonIds(limit,offset)
                            pokemonListApiCall()

                        }
                    }
                }

            }
        })



    }

    //retrieve pokemonList from apiCall
    fun pokemonListApiCall (){
        mViewModel.response.observe(viewLifecycleOwner, Observer {
            readyToChargeList = true
            listPokemonData = it.results as List<ResultsItem>
            updateUI(listPokemonData as ArrayList<ResultsItem>)
        })
    }

    //update itemList size
    private fun updateUI(results: ArrayList<ResultsItem>) {

        Log.d("updateUI", "updateData:" + results.size)
        adapter.addPokemonToList(results)
        adapter.setResults(results)
        adapter.notifyDataSetChanged()

    }

//    suspend fun pokeApiCall(){
//
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PokemonListFragmentBinding.bind(view)
    }
}

