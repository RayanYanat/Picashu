package com.example.picashu.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.picashu.R
import com.example.picashu.model.Card
import com.example.picashu.model.CardSetResponse.DataItem
import com.example.picashu.model.ResultsItem
import com.example.picashu.view.fragment.PokemonCardListFragment


class PokemonSetAdapter(private val listDataSet: ArrayList<DataItem>, private val listener: PokemonSetChilAdapter.ItemClickListener,private val listSeries : List<String>,private val listUserCard : List<Card>) : RecyclerView.Adapter<PokemonSetAdapter.PokeSetListViewHolder>(){

    private var mData: ArrayList<String> = ArrayList()
    private var listPokemonSeries = ArrayList<String>()
    private var mResult : List<Card> = ArrayList()



    fun setResults(data: ArrayList<String>) {
        mData = data
        Log.d("PokemonSetAdapter", "POKELISTDATA:${mData.size}")
    }

    fun setCardResults ( result : List<Card> ){
        mResult = result
        Log.d("pokemonADAPTER", "POKELISTCardDATA:$listUserCard")
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonSetAdapter.PokeSetListViewHolder {
        return PokemonSetAdapter.PokeSetListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.collection_set_item, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: PokeSetListViewHolder,
        position: Int
    ) {

        val serieItem = mData.get(position)

        holder.bind(listDataSet,serieItem,listener,listUserCard)

    }

    override fun getItemCount(): Int {
        return if (mData.isNotEmpty()) mData.size else 0
    }


    class PokeSetListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imageItem = view.findViewById<ImageView>(R.id.pokemon_set_pic)
        val nameItem = view.findViewById<TextView>(R.id.pokemon_set_name)
        val ratioItem = view.findViewById<TextView>(R.id.pokemon_set_ratio)
        val childRecyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_child_set_data)


        fun bind(setResult : ArrayList<DataItem>, series :String, clickListener: PokemonSetChilAdapter.ItemClickListener, listUserCard : List<Card>){

             val listPokemonSet: ArrayList<DataItem> = ArrayList()
             var seriesTotalCard = 0
            var seriesTotalCardOwned = 0

            setResult.forEach {
                if (it.series == series){
                    listPokemonSet.add(it)
                    seriesTotalCard += it.total!!
                }
            }

            listUserCard.forEach {
                if (it.serie == series){
                    seriesTotalCardOwned++
                }
            }

            ratioItem.text = "$seriesTotalCardOwned/${seriesTotalCard}"
            nameItem.text = series
            val adapter = PokemonSetChilAdapter(listPokemonSet,clickListener,listUserCard)
            adapter.setResults(listPokemonSet)
            childRecyclerView.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
            childRecyclerView.adapter = adapter
           // childRecyclerView.addItemDecoration(DividerItemDecoration(itemView.context, DividerItemDecoration.VERTICAL))

            itemView.setOnClickListener{

            }
        }

    }

    interface ItemClickListener {
        fun onItemClickListener(poke: ResultsItem)
    }




}