package com.example.picashu.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.picashu.R
import com.example.picashu.databinding.PokemonListFragmentBinding
import com.example.picashu.model.PokemonListeResponse
import com.example.picashu.model.ResultsItem
import com.example.picashu.view.adapter.PokemonListAdapter
import com.example.picashu.viewModel.PokemonListFragmentViewModel

class PokemonListFragment : Fragment(R.layout.pokemon_list_fragment),
    PokemonListAdapter.ItemClickListener {

    private lateinit var binding: PokemonListFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var mViewModel: PokemonListFragmentViewModel
    private lateinit var adapter: PokemonListAdapter

    private var readyToChargeList: Boolean = false
    private var offset = 0
    private var limit = 20

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    private val POKE_NAME = "POKE_NAME"

    val QUERY_PAGE_SIZE = 20

    private var listPokemonData = ArrayList<ResultsItem>()
    private var listToAddToAdapter = ArrayList<ResultsItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PokemonListFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        activity?.supportFragmentManager?.popBackStack()
        mViewModel = ViewModelProvider(this).get(PokemonListFragmentViewModel::class.java)
        recyclerView = binding.recyclerViewDataStat
        mViewModel.getPokemonIds(20, offset)
        pokemonListApiCall2()
        configureRecyclerView()

        return view
    }

    private fun configureRecyclerView() {

        adapter = PokemonListAdapter(listPokemonData, this)
        val layoutManager = GridLayoutManager(activity, 3)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        adapter.setResults(listPokemonData)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView1: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (isScrolling && visibleItemCount + firstVisibleItemPosition == totalItemCount) {
                    isScrolling = false

                    offset += 20
                    Log.d("pokemonApiCall", "offset: $offset) ")
                    mViewModel.getPokemonIds(20, offset)
                    pokemonListApiCall2()
                }


            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

        })


    }

    //retrieve pokemonList from apiCall
    fun pokemonListApiCall() {
//        mViewModel.response.observe(viewLifecycleOwner, Observer {
//            listPokemonData.addAll(it.results as ArrayList<ResultsItem>)
        Log.d("pokemonApiCall", "POKELISTapi: pokemonListApiCall ")
//            adapter.setResults(listPokemonData)
//            adapter.notifyDataSetChanged()
//            //updateUI(listPokemonData)
        //     })
    }

    private fun pokemonListApiCall2() {

        mViewModel.response.observe(viewLifecycleOwner, Observer { it: PokemonListeResponse ->
//            listPokemonData.addAll(it.results as ArrayList<ResultsItem>)
//            Log.d("pokemonApiCall", "POKELISTapi2: ${it.results.size}) ")

//            //updateUI(listPokemonData)
            val result = it.results
            Log.d("pokemonApiCall", "POKELISTapi2: ${it.results?.size}) ")
            result?.forEach {
                if (!listPokemonData.contains(it)) {
                    listPokemonData.add(it!!)
                }
            }

            adapter.notifyDataSetChanged()
        })
    }

    //update itemList size
    private fun updateUI(results: ArrayList<ResultsItem>) {

        Log.d("updateUI", "updateData:" + results.size)
        // adapter.addPokemonToList(results)
        // adapter.setResults(results)
        adapter.notifyDataSetChanged()

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PokemonListFragmentBinding.bind(view)
        val searchButton = binding.summonerSearch
        val searchEdit = binding.pokemonNameLayout

        searchButton.setOnClickListener {
            val pokemonName = searchEdit.text.toString()
            val bundle = Bundle()
            val pokemonCardListFragment = PokemonCardListFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            bundle.putString(POKE_NAME,pokemonName)
            pokemonCardListFragment.arguments = bundle
            transaction.replace(R.id.main_fragment, pokemonCardListFragment).addToBackStack(null).commit()
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("pokemonListFrag", "onresume:" )
    }

    override fun onItemClickListener(poke: ResultsItem) {

        val bundle = Bundle()
        val pokemonCardListFragment = PokemonCardListFragment()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        bundle.putString(POKE_NAME, poke.name)
        pokemonCardListFragment.arguments = bundle
        transaction.replace(R.id.main_fragment, pokemonCardListFragment).addToBackStack(null).commit()

    }
}



