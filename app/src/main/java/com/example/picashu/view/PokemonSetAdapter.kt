package com.example.picashu.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.picashu.R
import com.example.picashu.model.CardSetResponse.DataItem
import com.example.picashu.model.ResultsItem


class PokemonSetAdapter(private val listUser: List<DataItem>, private val listener: PokemonSetAdapter.ItemClickListener) : RecyclerView.Adapter<PokemonSetAdapter.PokeSetListViewHolder>(),PokemonSetChilAdapter.ItemClickListener {

    private var mData: ArrayList<DataItem> = ArrayList()

    fun setResults(data: ArrayList<DataItem>) {
        mData = data
        Log.d("PokemonSetAdapter", "POKELISTDATA:${mData.size}")
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
        holder: PokemonSetAdapter.PokeSetListViewHolder,
        position: Int
    ) {
        val pokeItem = mData.get(position)
        val adapter = PokemonSetChilAdapter(mData,this)
        adapter.setResults(mData)

        holder.bind(pokeItem)

        holder.childRecyclerView.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL,false)
        holder.childRecyclerView.adapter = adapter

    }

    override fun getItemCount(): Int {
        return if (mData.isNotEmpty()) mData.size else 0
    }


    class PokeSetListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imageItem = view.findViewById<ImageView>(R.id.pokemon_set_pic)
        val nameItem = view.findViewById<TextView>(R.id.pokemon_set_name)
        val childRecyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_child_set_data)


        fun bind(result : DataItem){



            if (result.name != null){

                nameItem.text = result.series
                Glide.with(itemView).load(result.images?.logo).into(imageItem)



            }

        }


    }

    interface ItemClickListener {
        fun onItemClickListener(poke: ResultsItem)
    }

    override fun onItemClickListener(poke: ResultsItem) {
        Log.d("pokemonADAPTER", "item clicked !! ")
    }


}