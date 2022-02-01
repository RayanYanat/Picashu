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
import com.example.picashu.Trade
import com.example.picashu.databinding.ConcludedTradeFragmentBinding
import com.example.picashu.view.adapter.ConcludedTradeAdapter
import com.example.picashu.viewModel.ConcludedTradFragmentViewModel
import com.google.firebase.auth.FirebaseAuth

class ConcludedTradFragment : Fragment(R.layout.concluded_trade_fragment) {

    private lateinit var binding: ConcludedTradeFragmentBinding
    private lateinit var mViewModel: ConcludedTradFragmentViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ConcludedTradeAdapter

    val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
    private var listConcludedTradeData = ArrayList<Trade>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ConcludedTradeFragmentBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(this).get(ConcludedTradFragmentViewModel::class.java)
        recyclerView = binding.concludedTradeRecyclerView

        mViewModel.getSavedConcludedTrade(currentUserId)
        retrieveConcludedTrads()
        configureRecyclerView()

        return binding.root
    }

    private fun configureRecyclerView(){

        adapter = ConcludedTradeAdapter(listConcludedTradeData)
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        adapter.setResults(listConcludedTradeData)

    }

    private fun retrieveConcludedTrads(){
        mViewModel.concludedTrade.observe(viewLifecycleOwner,{
            listConcludedTradeData.addAll(it)
            Log.d("retrieveConcludedTrade", "currentList:$listConcludedTradeData zt size ${listConcludedTradeData.size}")
             adapter.notifyDataSetChanged()
        })
    }

}