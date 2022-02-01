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
import com.example.picashu.model.Card
import com.example.picashu.model.CardSetResponse.DataItem
import com.example.picashu.model.ResultsItem
import com.example.picashu.view.adapter.PokemonSetAdapter
import com.example.picashu.view.adapter.PokemonSetChilAdapter
import com.example.picashu.viewModel.PokemonCollectionFragmentViewModel
import com.google.firebase.auth.FirebaseAuth

class PokemonCollectionFragment : Fragment(R.layout.collection_fragment_list), PokemonSetAdapter.ItemClickListener,
    PokemonSetChilAdapter.ItemClickListener {

    private lateinit var binding: CollectionFragmentListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var mViewModel: PokemonCollectionFragmentViewModel
    private lateinit var adapter: PokemonSetAdapter

    private var listPokemonSetData = ArrayList<DataItem>()
    private var listPokemonSeries = ArrayList<String>()
    private var listUserCardData = ArrayList<Card>()


    private val POKE_SET = "POKE_SET"

    val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CollectionFragmentListBinding.inflate(inflater, container, false)
        val view = binding.root
        mViewModel = ViewModelProvider(this).get(PokemonCollectionFragmentViewModel::class.java)
        recyclerView = binding.recyclerViewSetData
        mViewModel.getPokemonSets("","")
        mViewModel.getSavedUserCards(currentUserId)

        retrieveUserCard()
        pokemonSetListApiCall()
        configureRecyclerView()
        return view
    }

    private fun configureRecyclerView() {
        adapter = PokemonSetAdapter(listPokemonSetData, this,listPokemonSeries,listUserCardData)
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

    }

    private fun retrieveUserCard(){
        mViewModel.savedCard.observe(viewLifecycleOwner,{
            listUserCardData.addAll(it)
            Log.d("retrieveUserCard", "currentCardList:$listUserCardData zt size ${listUserCardData.size}")
             adapter.notifyDataSetChanged()
        })
    }

    private fun pokemonSetListApiCall() {
        mViewModel.setResponse.observe(viewLifecycleOwner, {
            listPokemonSetData.addAll(it.data as Collection<DataItem>)

            it.data.forEach { dataItem ->

                if (!listPokemonSeries.contains(dataItem.series)) {
                    listPokemonSeries.add(dataItem.series.toString())
                    Log.d("SetAdapter", "PokemonCollectionFragment:$listPokemonSeries")
                }

                adapter.setResults(listPokemonSeries)
                adapter.setCardResults(listUserCardData)
                adapter.notifyDataSetChanged()

            }


        })




    }

    override fun onItemClickListener(poke: ResultsItem) {
    }

    override fun onItemClickListener(poke: DataItem) {

        Log.d("SetAdapter", "itemClicked !! ")

        val bundle = Bundle()
        val pokemonCardListFragment = PokemonCardListFragment()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        bundle.putString(POKE_SET, poke.id)
        pokemonCardListFragment.arguments = bundle
        transaction.replace(R.id.main_fragment, pokemonCardListFragment).commit()
    }

}