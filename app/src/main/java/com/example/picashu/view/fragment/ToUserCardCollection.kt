package com.example.picashu.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.picashu.R
import com.example.picashu.databinding.ToUserCardCollectionBinding
import com.example.picashu.model.Card
import com.example.picashu.view.adapter.CardAdapter
import com.example.picashu.view.adapter.ItemClickListener
import com.example.picashu.viewModel.ToUserCardCollectionViewModel

class ToUserCardCollection :Fragment(R.layout.to_user_card_collection), ItemClickListener {

    private lateinit var mViewModel: ToUserCardCollectionViewModel
    private lateinit var recyclerView : RecyclerView
    private lateinit var binding : ToUserCardCollectionBinding
    private lateinit var adapter : CardAdapter
    private var listUserCardData = ArrayList<Card>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val currentCardSeries = requireArguments().getString("POKE_SERIES")
        val currentCardSet = requireArguments().getString("POKE_SET")
        val selectedUserId = requireArguments().getString("USER_ID")

        binding = ToUserCardCollectionBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(this).get(ToUserCardCollectionViewModel::class.java)
        recyclerView = binding.toUserCardRecyclerView

        if (currentCardSeries != null && selectedUserId != null){
            mViewModel.getTradeCardListBySerie(selectedUserId,currentCardSeries)
        }else if (currentCardSet != null && selectedUserId != null) {
            mViewModel.getTradeCardListBySet(selectedUserId,currentCardSet)
        }

        if (currentCardSeries != null){
            retrieveCardSeriesData()
        }

        if (currentCardSet != null){
            retrieveCardSetData()
        }

        ConfigureRecyclerView()
        return binding.root
    }

    private fun ConfigureRecyclerView(){
        adapter = CardAdapter(this,listUserCardData)
        val layoutManager = GridLayoutManager(activity, 3)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        adapter.setResults(listUserCardData)
    }

    private fun retrieveCardSetData(){
        mViewModel.savedCardBySet.observe(viewLifecycleOwner, {
            Log.d("ToUserCardCollection", "SetCardList: $it")
            listUserCardData.addAll(it)
            Log.d("ToUserCardCollection", "SetCardList: $listUserCardData")
            adapter.notifyDataSetChanged()
        })
    }

    private fun retrieveCardSeriesData(){
        mViewModel.savedCardBySerie.observe(viewLifecycleOwner,{
            Log.d("ToUserCardCollection", "SeriesCardList: $it")
            listUserCardData.addAll(it)
            Log.d("ToUserCardCollection", "SeriesCardList: $listUserCardData")
            adapter.notifyDataSetChanged()

        })
    }

    override fun onItemClickListener(poke: Card) {
        TODO("Not yet implemented")
    }
}